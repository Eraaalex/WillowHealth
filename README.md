# Установка

Для установки программы необходимо скачать файл apk из папки app apk file и установить его на устройство.
Либо же можно скачать проект и запустить его в Android Studio.

# Запуск программы
При первом запуске приложения пользователь перенаправляется на экран регистрации/авторизации. Не авторизованному в системе оператору доступен только функционал сброса пароля и авторизации (ввода почты, пароля и номера телефона) на экране авторизации.

## Экран регистрации авторизации
На экране пользователь уже имеет аккаунт в приложении, для входа ему необходимо ввести данные:

1. Почта

2. Пароль

Если пользователь не зарегистрирован в системе, то ему необходимо заполнить поля ввода:

1. Почта

2. Телефон

3. Пароль

Так же на экране пользователь имеет возможность нажать на кнопку сброса пароля и будет показано окно с возможностью ввода почты, при вводе которой инструкции по сбросу пароля будут отправлены на указанную почту, если такой пользователь имеет аккаунт в системе.
В случае ввода некорректных данных пользователю отображается соответствующее сообщение “Sign-In failed” или “Sign-up failed”.
После регистрации/авторизации первоначально на устройстве пользователь получает запрос на разрешение использование его данных с Google Fit, в случае положительного ответа пользователь перенаправляется на экран опроса, если текущее время не позднее 13:00 и не ранее 4:00 и ранее за текущий день пользователь не отправлял результаты опроса в систему.

## Экран опроса

Данный экран представляет собой несколько вопросов с возможностью вариантов ответа используя радиокнопки для оценки качества сна по шкале от 1 до 10, Checkbox для отметки типов нарушений сна. А также при помощи средства для выбора времени (time picker) поля для ввода времени начала и окончания сна.
При нажатии на кнопку отправки опроса пользователь будет перенаправлен на главный экран, подраздел Представления (Insights)

## Экран Представления

Данный экран отображает информацию о сне, шагах, потраченных калориях пользователем, а также длительности сна за текущую неделю
В нижней панели данного экрана пользователь при  нажатии на иконку экрана чата будет перенаправлен на экран чата.
При нажатии  на иконку экрана настроек будет перенаправлен на экран настроек.

## Экран чата

Экран представляет собой чат с чат-ботом. На данном экране отображается пояснительное сообщение от чат-бота с описанием его назначения.
При вводе сообщения внизу экрана и нажатии кнопки отправки, данное сообщение отображается в списке сообщений с чат-ботом.
В ответ на отправку сообщения пользователем программа отобразит ответ чат-бота.   


## Экран настроек

При нажатии на кнопку “logout” пользователь будет перенаправлен на страницу авторизации/регистрации и завершит сессию аккаунта.
При нажатии кнопки ночной режим, если данный режим темы был включен, он будет выключен. Если же данный режим был выключен, то он установится и тема приложения сменится на темную.
