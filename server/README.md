# Нужно в папку сервера поместить модель
ссылка
# Установить зависимости для python >= 3 version
pip install -r requirements.txt

# node -v >= 13.7.0

# Установить зависимости
npm i
# start server
npm run start

# Загрузить одно изображение
POST '/api/uploadSingleImage'
{file: File}

# Загрузить несколько изображений
POST '/api/uploadImages'
{photos: [File]}
