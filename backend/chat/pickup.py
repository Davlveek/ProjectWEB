from chat.models import User
from random import randint

class Pickuper():
    #инициализируем статические списки гтовых к игре мужчин и женщин
    PEOPLE_IN_GAME = 6
    male_queue = []
    female_queue = []

    def check_in_pickup(self, user_object):
        choice_for_agender = 0
        #если человек не указал_а пол кидаем е_ё рандомно
        #вносим зареганого пользователя в список
        if user_object.gender == 'o':
            choice_for_agender = randint(1, 2)
        if user_object.gender == 'm' or choice_for_agender == 1:
            self.male_queue.append(user_object.id)
        elif user_object.gender == 'f' or choice_for_agender == 2:
            self.female_queue.append(user_object.id)

    def pull_people(self):
        #если в каком-то списке меньше N человек — невозможно создать комнату
        if len(self.male_queue) < self.PEOPLE_IN_GAME or len(self.female_queue) < self.PEOPLE_IN_GAME:
            return False
        #выбираем первые N человек в каждой очереди
        fem_part = self.female_queue[0:self.PEOPLE_IN_GAME-1]
        male_part = self.male_queue[0:self.PEOPLE_IN_GAME-1]
        del self.female_queue[0:self.PEOPLE_IN_GAME-1]
        del self.male_queue[0:self.PEOPLE_IN_GAME-1]
        i = 0
        #для каждой девушки создается список партнеров, причем для первой он будет [1,2,...,n], для второй [2,3,...n,1]
        #для последней [n,1,...,n-1]
        for female in fem_part:
            User.get_partners(female, self.rotate_list_left(male_part, i))
        temp_fem_part = fem_part.reverse()
        i = 1
        #для первого парня [1,n,n-1,...,2]
        for male in male_part:
            User.get_partners(male, self.rotate_list_right(temp_fem_part, i))



    def rotate_list_left(self, list_to_rotate, n):
        #вращает список, например rotate_list_left([1,2,3,4], 1) вернет [2, 3, 4, 1]
        return list_to_rotate[n:] + list_to_rotate[:n]

    def rotate_list_right(self, list_to_rotate, n):
        # вращает список, например rotate_list_right([1,2,3,4], 1) вернет [4, 1, 2, 3]
        return list_to_rotate[-n:] + list_to_rotate[:-n]
