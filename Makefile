# Makefile
install: # очистка результатов предыдущей сборки и запуск новой
	./gradlew clean install

run-dist: # запуск исполняемого файла
	./build/install/app/bin/app

check-updates: # проверка обновления зависимостей и плагинов
	./gradlew dependencyUpdates

run: # запуск приложения
	./gradlew run

lint: # проверка стиля кодирования
	./gradlew checkstyleMain

clean: #очистка
	./gradlew clean

.PHONY: build
build: clean # создание билда
	./gradlew build

