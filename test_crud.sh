#!/bin/bash

BASE_URL="http://localhost:8080/api/posts"

echo "=== 1️⃣ Получаем все посты (должно быть пусто) ==="
curl -s $BASE_URL
echo -e "\n"

echo "=== 2️⃣ Создаём новый пост ==="
RESPONSE=$(curl -s -X POST -H "Content-Type: application/json" -d '{"content":"Hello World"}' $BASE_URL)
echo "Ответ сервера при создании: $RESPONSE"
ID=$(echo $RESPONSE | sed -E 's/.*"id":([0-9]+).*/\1/')
echo "Создан пост с ID = $ID"
echo -e "\n"

echo "=== 3️⃣ Получаем все посты после создания ==="
curl -s $BASE_URL
echo -e "\n"

echo "=== 4️⃣ Получаем пост по ID ==="
curl -s $BASE_URL/$ID
echo -e "\n"

echo "=== 5️⃣ Обновляем пост ==="
curl -s -X PUT -H "Content-Type: application/json" -d '{"content":"Updated Content"}' $BASE_URL/$ID
echo -e "\n"

echo "=== 6️⃣ Получаем пост после обновления ==="
curl -s $BASE_URL/$ID
echo -e "\n"

echo "=== 7️⃣ Мягко удаляем пост ==="
curl -s -X DELETE $BASE_URL/$ID
echo "Пост с ID = $ID удалён"
echo -e "\n"

echo "=== 8️⃣ Получаем все посты после удаления (должно быть пусто) ==="
curl -s $BASE_URL
echo -e "\n"

echo "=== 9️⃣ Проверяем получение удалённого поста (должно вернуть 404) ==="
HTTP_STATUS=$(curl -s -o /dev/null -w "%{http_code}" $BASE_URL/$ID)
echo "HTTP статус при получении удалённого поста: $HTTP_STATUS"
