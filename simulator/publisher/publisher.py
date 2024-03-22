import configparser
import logging
import pika

from publisher.connection_pool import ConnectionPool


class Publisher:

    def __init__(self, config_path='config/config.ini'):
        self._config = configparser.ConfigParser()
        self._config.read(config_path)
        self._connection_pool = ConnectionPool(config_path)

    def _get_connection(self):
        return self._connection_pool.get_connection()

    def publish(self, data):
        connection = self._get_connection()
        try:
            channel = connection.channel()
            exchange_name = self._config.get('RabbitMQ', 'exchange_name')
            routing_key = self._config.get('RabbitMQ', 'routing_key')
            queue_name = self._config.get('RabbitMQ', 'queue_name')
            channel.exchange_declare(exchange=exchange_name, exchange_type='direct', durable=True)
            channel.queue_declare(queue=queue_name, durable=True)
            channel.queue_bind(exchange=exchange_name, queue=queue_name, routing_key=routing_key)

            channel.basic_publish(
                exchange=exchange_name,
                routing_key=routing_key,
                body=data.to_json(),
                properties=pika.BasicProperties(
                    delivery_mode=2,
                )
            )
            logging.info(f" [x] Sent: {data.to_json()}")
        except pika.exceptions.AMQPError as e:
            logging.error(f"Error during RabbitMQ operation: {e}")
        except Exception as e:
            logging.error(f"Unexpected error: {e}")
        finally:
            # Return the connection to the pool
            self._connection_pool.pool.append(connection)

