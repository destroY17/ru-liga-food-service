# Сервис доставки еды

## Сервис включает в себя следующие модули:
- order-service - предназначен для работы с заказами, которые поступил от клиентов
- kitchen-service - предназначен для обработки и приготовления заказа
- delivery-service - предназначен для доставки заказа клиенту
- notification-service - предназначен для обмена информацией о заказе между сервисами
- migration - миграция баз данных
- common - содержит бизнес-логику общую для сервисов
- auth-service - предназначен для авторизации пользователя
- cloud-gateway - облачный шлюз

## Работа с сервисом

### Подготовка к запуску
1. Сборка проекта с помощью maven - mvn install
2. Создание базы данных приложения с помощью миграции liquibase -  maven плагина "liquibase:update"
3. Необходимо наличие брокера сообщений Rabbit-MQ

### Запуск проекта
1. Запуск сервиса авторизации - auth-service
2. Запуск cloud-gateway
3. Запуск notification-service, order-service, kitchen-service, delivery-service

### Работа с проектом
Для доступа к API сервисов необходимо пройти аутентификацию и авторизацию.

### Получение токена в Postman
1. Создать запрос
2. Authorization -> Type -> Oauth 2.0
3. В форме Configure New Token необходимо заполнить поля следующими значениями:
   - Grant type -> Authorization Code
   - Callback URL -> http://127.0.0.1:8080/login/oauth2/code/gateway
   - Auth URL -> http://localhost:8080/login
   - Access Token URL -> http://localhost:9000/oauth2/token
   - Client ID -> gateway
   - Client secret -> secret
   - Scope -> openid,message.read
4. Внизу формы - Clear cookies -> Get New Access Token
5. Появится форма для ввода логина и пароля: user и password по умолчанию соотвественно
6. При успешной авторизации - postman вышлет токен.

### Сценарий работы
1. Клиент делает заказ через order-service.
2. Клиент производит оплату заказа через order-service.
3. Оплаченный заказ автоматически с помощью rabbit-mq отправляется в notification-service, который отправляет уведомление kitchen-service о появлении нового заказа.
4. Kitchen-service может принять или отклонить заказ. В случае отклонения - оповещение order-service через notification-service и возврат средств клиенту. В случае принятия заказа - обновление статуса заказа и оповещение order-service.
5. По завершении готовки заказа, kitchen-service с помощью соответсвующего метода API завершает работу над заказом и оповещает об этом order-service и delivery-service.
6. delivery-service принимает сообщение о готовности заказа, уведомляет об этом свободных курьеров. delivery-service собирает заказ, теперь он доступен к доставке, оповещает order-service.
7. Курьер с помощью delivery-service может найти доступные для доставки заказы и принять один из них, оповещает order-service.
8. Когда доставка завершена - курьер обновляет статус заказа, оповещает order-service.

К каждому сервису приложена postman коллекция в папке resources
