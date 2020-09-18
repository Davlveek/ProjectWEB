import json
from channels.generic.websocket import AsyncWebsocketConsumer
from asgiref.sync import async_to_sync, sync_to_async
from .serializers import UserSerializer

class ChatQueue(AsyncWebsocketConsumer):

    # WEBSOCKET INTERACTION
    async def connect(self):
        if self.scope['user'] is not None:
            self.user = await self.scope['user']
        else:
            await self.close()
        await self.accept()
    

    async def receive(self, text_data):
        user = UserSerializer(self.user).data
        data_json = json.loads(text_data)
        print(data_json)
        if data_json['type'] == 'init.connection':
            await self.channel_layer.group_add(
                'queue',
                self.channel_name
            )

            await self.channel_layer.group_send(
                'queue',
                {
                    'type': 'request.connection',
                    'user': user,
                    'channel': self.channel_name,
                }
            )

        elif data_json['type'] == 'accept.connection':
            chatter = data_json['user']
            channel = data_json['channel']

            await self.channel_layer.group_add(
                user['name'] + '-' + chatter['name'],
                channel
            )

            await self.channel_layer.group_send(
                user['name'] + '-' + chatter['name'],
                {
                    'type': 'request.connection',
                    'user': user,
                    'channel': self.channel_name,
                }
            )

        elif data_json['type'] == 'connection.established':
            chatter = data_json['user']
            channel = data_json['channel']
            
            await self.channel_layer.group_send(
                user['name'] + '-' + chatter['name'],
                {
                    'type': 'connection.established',
                }
            )

            await self.channel_layer.group_send(
                chatter['name'] + '-' + user['name'],
                {
                    'type': 'connection.established',
                }
            )

        elif data_json['type'] == 'deny.connection':
            chatter = data_json['user']
            channel = data_json['channel']

            await self.channel_layer.group_add(
                chatter['name'] + '-' + user['name'],
                channel
            )

            await self.channel_layer.group_discard(
                chatter['name'] + '-' + user['name'],
                self.channel_name
            )

            await self.channel_layer.group_send(
                chatter['name'] + '-' + user['name'],
                {
                    'type': 'deny.connection',
                    'user': user,
                    'channel': self.channel_name,
                }
            )

        elif data_json['type'] == 'reset.connection':
            chatter = data_json['chatter']
            
            await self.channel_layer.group_discard(
                user['name'] + '-' + chatter['name'],
                self.channel_name
            )

            await self.channel_layer.group_add(
                'queue',
                self.channel_name
            )

            await self.channel_layer.group_send(
                'queue',
                {
                    'type': 'request.connection',
                    'user': user,
                    'channel': self.channel_name,
                }
            )

        elif data_json['type'] == 'message':
            message = data_json['message']
            chatter = data_json['chatter']

            await self.channel_layer.group_send(
                user['name'] + '-' + chatter['name'],
                {
                    'type': 'message',
                    'message': message,
                }
            )

            await self.channel_layer.group_send(
                chatter['name'] + '-' + user['name'],
                {
                    'type': 'message',
                    'message': message,
                }
            )


    async def disconnect(self, close_code):
        pass


    # CHANNEL LAYER INTERACTION
    async def request_connection(self, data):
        user = UserSerializer(self.user).data

        if user['gender'] != data['user']['gender']:
            await self.send(text_data=json.dumps(data))


    async def connection_established(self, data):
        user = UserSerializer(self.user).data

        await self.channel_layer.group_discard(
            'queue',
            self.channel_name
        )

        await self.send(text_data=json.dumps(data))
  

    async def deny_connection(self, data):
        user = UserSerializer(self.user).data
        chatter = data['user']

        await self.channel_layer.group_discard(
            user['name'] + '-' + chatter['name'],
            self.channel_name
        )

        await self.send(text_data=json.dumps(data))


    async def message(self, data):
        user = UserSerializer(self.user).data
        await self.send(text_data=json.dumps(data))
