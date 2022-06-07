# DevOps_deeplay_task


## Задание №1

### Формулировка задания №1

> Имеется лог файл log.txt
>
> Необходимо при помощи любых локальных инструментов
> (bash, python ...) получить отсортированный список
> всех значений sid в строках с IP=10.1.192.38
> 
> Значения sid должны быть без ограничичивающих
> символов '/', кроме тех, что находятся внутри значения.

Содержимое файла log.txt:

```
10.1.192.62 - - [18/Aug/2020:01:08:50 +0000] "POST /zabbix/jsrpc.php?sid=/b59ea3cc3hryh446/&type=9&method=screen.get&timestamp=1597712895283&mode=0&screenid=58&groupid=&hostid=10262&pageFile=screens.php&profileIdx=web.screens.filter&profileIdx2=58&screenitemid=9594&from=2020-08-14%2000%3A00%3A00&to=2020-08-14%2006%3A00%3A00 HTTP/1.1" 200 845 "Chrome/84.0.4147.125 Safari/537.36"
10.1.192.38 - - [18/Aug/2020:01:08:50 +0000] "POST /zabbix/jsrpc.php?sid=/m85486u/k7kkjklij/&type=9&method=screen.get&timestamp=1597712895291&mode=0&screenid=58&groupid=&hostid=10262&pageFile=screens.php&profileIdx=web.screens.filter&profileIdx2=58&screenitemid=9595&from=2020-08-14%2000%3A00%3A00&to=2020-08-14%2006%3A00%3A00 HTTP/1.1" 200 848 "Chrome/84.0.4147.125 Safari/537.36"
10.1.192.38 - - [18/Aug/2020:01:08:50 +0000] "POST /zabbix/jsrpc.php?sid=/kjtg986u76klju89/&type=9&method=screen.get&timestamp=1597712895338&mode=0&screenid=58&groupid=&hostid=10262&pageFile=screens.php&profileIdx=web.screens.filter&profileIdx2=58&screenitemid=9597&from=2020-08-14%2000%3A00%3A00&to=2020-08-14%2006%3A00%3A00 HTTP/1.1" 200 840 "Chrome/84.0.4147.125 Safari/537.36"
10.1.192.38 - - [18/Aug/2020:01:08:50 +0000] "POST /zabbix/jsrpc.php?sid=/90ghdkyj78jyyfui/&type=9&method=screen.get&timestamp=1597712895349&mode=0&screenid=58&groupid=&hostid=10262&pageFile=screens.php&profileIdx=web.screens.filter&profileIdx2=58&screenitemid=9598&from=2020-08-14%2000%3A00%3A00&to=2020-08-14%2006%3A00%3A00 HTTP/1.1" 200 828 "Chrome/84.0.4147.125 Safari/537.36"
10.1.192.67 - - [18/Aug/2020:01:08:50 +0000] "POST /zabbix/jsrpc.php?sid=/htrifh6gb476fdhr/&type=9&method=screen.get&timestamp=1597712895355&mode=0&screenid=58&groupid=&hostid=10262&pageFile=screens.php&profileIdx=web.screens.filter&profileIdx2=58&screenitemid=9599&from=2020-08-14%2000%3A00%3A00&to=2020-08-14%2006%3A00%3A00 HTTP/1.1" 200 873 "Chrome/84.0.4147.125 Safari/537.36"
```

### Реализация

#### Мои рассуждения

Вероятно, упорядоченный список значений конкретного параметра
в строках с конкретным IP нам может понадобиться либо
как входной параметр для некоторой программы обработки
(скорее всего одноразово), либо для некоторого визуального
анализа.

В таком случае логичнее будет написать одну строчку
в терминале, которая выведет на экран(или в файл) отсротированный
список значений.

В формулировке задачи не было указано, что файл большой
(что есть ограничения по используемой памяти), значит в
этом случае нет необходимости сортировку слиянием
(можно разбить данные на файлы, каждый из них отсортировать
любым методом, сортировкой слиянием создать один файл с
упорядоченными значениями sid от конкретного IP).

#### Решение и объяснение

```bash
cat input.txt | grep "^10.1.192.38" | awk -F 'sid=/' '{print $2}' | awk -F '/&type' '{print $1}' | sort
```

1. `cat input.txt` - выводит в стандартный вывод(stdout)
    содержимое файла.
2. `|` - стандартый вывод(stdout) программы слева пренаправляет
    на стандартный ввод(stdin) программы справа.
3. `grep "^10.1.192.38"` - выводит в стандартный вывод(stdout)
    только те строки, которые начинаются с подстроки 10.1.192.38.
4. `awk -F 'sid=/' '{print $2}'` - выводит в стандартный вывод(stdout)
   строки, у которых отсутствует часть до значения sid(удалено все до разделителя 'sid=/').
5. `awk -F '/&type' '{print $1}'` - выводит в стандартный вывод(stdout)
   строки, у которых отсутствует часть после значения sid(удалено все после разделителя '/&type').
6. `sort` - простая сортировка строк из стандартного потока ввода(stdin) по алфавиту.

Результат выполнения команды:

```
90ghdkyj78jyyfui
kjtg986u76klju89
m85486u/k7kkjklij
```

Сортировка работает корректно.

## Задание №2

### Формулировка задания №2

> Имеется app.jar файл.
> 
> Для его запуска используется команда `java -jar app.jar some_out_file "Service is working!"`
> 
> Напишите простой демон для systemd (Linux),
> который будет поддерживать работу приложения и
> перезапускать его в случае выхода из строя процесса.
> 
> Необходимо сделать защиту от зацикливания перезапусков,
> когда процесс постоянно выходит из строя.

### Реализация

#### Мои рассуждения

Первое, что хочется сделать - посмотреть доступный исходный код приложения(частично есть такая возможность).

IntelliJ IDEA Community Edition может показать код файла [App.class(App.java)](task2/App.java). Изучив код, мы можем понять, что приложение
каждые 2 секунды пишет в файл(путь до него передается первым параметром) сообщение(передается вторым параметром).

Теперь, стоит запустить и понаблюдать за работой и результатом работы приложения.

Запустили его приведенной в задании командой в виртальной машине (GNOME Boxes) Ubuntu 22.04 .
Убедились, что приложение создает файл и пишет в него сообщения.

Что бы написать демон - достаточно создать файл `название.service`, расположить
его в каталоге `/etc/systemd/system/` и перезагрузить systemd командой `systemctl daemon-reload`

#### Решение и объяснение

Создадим файл [AppDevOpsTask.service](task2/AppDevOpsTask.service) со 
следующим содержимым:

```ini
[Unit]
Description=Deeplay Java-app daemon

[Service]
Type=simple
WorkingDirectory=/home/sergey/DevOpsTask2
ExecStart=/usr/bin/java -jar app.jar /home/sergey/DevOpsTask2/file.txt "Service is working!"
ExecStop=/usr/bin/kill -9 $MAINPID
User=sergey
Restart=on-failure
RestartSec=10s

[Install]
WantedBy=multi-user.target
```

Переместим этот файл в каталог `/etc/systemd/system/`
и выполним команду:
```shell
systemctl daemon-reload
```

Для запуска демона используем команду:
```shell
systemctl start AppDevOpsTask.service
```
Для остановки демона используем команду:
```shell
systemctl stop AppDevOpsTask.service
```

В результате работы приложения создается(если не существует) [файл](task2/file.txt), в
него каждые 2 секунды дописывается строка. Содердимое файла выглядит
следующим образом:
```
1) 07.06.2022 16:06:27   Service is working!
2) 07.06.2022 16:06:30   Service is working!
3) 07.06.2022 16:06:32   Service is working!
4) 07.06.2022 16:06:34   Service is working!
5) 07.06.2022 16:06:36   Service is working!
6) 07.06.2022 16:06:38   Service is working!
7) 07.06.2022 16:06:40   Service is working!
8) 07.06.2022 16:06:42   Service is working!
9) 07.06.2022 16:06:44   Service is working!
10) 07.06.2022 16:06:46   Service is working!
11) 07.06.2022 16:06:48   Service is working!
1) 07.06.2022 16:08:12   Service is working!
2) 07.06.2022 16:08:14   Service is working!
3) 07.06.2022 16:08:16   Service is working!
4) 07.06.2022 16:08:18   Service is working!
5) 07.06.2022 16:08:20   Service is working!
6) 07.06.2022 16:08:22   Service is working!
7) 07.06.2022 16:08:24   Service is working!
8) 07.06.2022 16:08:26   Service is working!
9) 07.06.2022 16:08:28   Service is working!
10) 07.06.2022 16:08:30   Service is working!
11) 07.06.2022 16:08:32   Service is working!
12) 07.06.2022 16:08:34   Service is working!
13) 07.06.2022 16:08:36   Service is working!
14) 07.06.2022 16:08:38   Service is working!
15) 07.06.2022 16:08:40   Service is working!
16) 07.06.2022 16:08:42   Service is working!
17) 07.06.2022 16:08:44   Service is working!
18) 07.06.2022 16:08:46   Service is working!
19) 07.06.2022 16:08:48   Service is working!
20) 07.06.2022 16:08:50   Service is working!
21) 07.06.2022 16:08:52   Service is working!
22) 07.06.2022 16:08:54   Service is working!
23) 07.06.2022 16:08:56   Service is working!
```

Что бы приложение стартовало при включении системы необходимо выплнить команду:
```shell
systemctl enabled AppDevOpsTask.service
```