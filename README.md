# ProjectWEB

Локально запускал редис через  
docker run -p 6379:6379 -d redis:5  
Нужен для чата

## Чтобы запустить бэк:
ставим питон 3  
cd backend  
python -m venv env  
на винде env/Scripts/activate  
на линухе source env/bin/activate  
python -m pip install -r requirements.txt  
python manage.py migrate  
python manage.py runserver  
  
## Чтобы запустить фронт:  
ставим node.js  
cd frontend  
npm install  
npm run serve  
