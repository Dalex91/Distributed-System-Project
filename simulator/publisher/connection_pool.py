import configparser
import threading

import pika


class ConnectionPool:
    def __init__(self, config_path='config/config.ini'):
        self._config = configparser.ConfigParser()
        self._config.read(config_path)
        self._pool = []
        self._lock = threading.Lock()

    def get_connection(self):
        with self._lock:
            if not self._pool:
                connection = self._create_connection()
                self._pool.append(connection)
            return self._pool.pop()

    def _create_connection(self):
        host = self._config.get('RabbitMQ', 'host')
        port = int(self._config.get('RabbitMQ', 'port'))
        username = self._config.get('RabbitMQ', 'username')
        password = self._config.get('RabbitMQ', 'password')
        virtual_host = self._config.get('RabbitMQ', 'virtual_host')
        credentials = pika.PlainCredentials(username, password)
        parameters = pika.ConnectionParameters(host, port, virtual_host=virtual_host, credentials=credentials)
        return pika.BlockingConnection(parameters)

    @property
    def pool(self):
        return self._pool
