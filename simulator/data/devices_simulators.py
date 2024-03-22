import pandas as pd
import threading
import time

from queue import Queue
from model.measurement_data import MeasurementData

from publisher.publisher import Publisher
from datetime import datetime

csv_url = "https://dsrl.eu/courses/sd/materials/sensor.csv"
device_name = "Device "
publisher = Publisher()


def read_value(queue, id):
    while True:
        value = queue.get()
        print(f"{threading.current_thread().name}: {value}")
        publisher.publish(
            MeasurementData(
                timestamp=int(datetime.now().timestamp() * 1000),
                device_id=id,
                measurement_value=value
            )
        )
        time.sleep(5)


def _init_queue():
    df = pd.read_csv(csv_url, header=None, names=["sensor"])
    values = df["sensor"].tolist()

    data_queue = Queue()
    for value in values:
        data_queue.put(value)
    return data_queue


def configure_readers(device_ids):
    data_queue = _init_queue()
    threads = []

    for ID in device_ids:
        thread = threading.Thread(target=read_value, args=(data_queue, ID,), name=device_name + str(ID))
        threads.append(thread)

    for thread in threads:
        thread.start()

    for thread in threads:
        thread.join()
