import json


class MeasurementData:
    def __init__(self, timestamp, device_id, measurement_value):
        self.timestamp = timestamp
        self.device_id = device_id
        self.measurement_value = measurement_value

    def to_json(self):
        return json.dumps(
            {
                "timestamp": self.timestamp,
                "device_id": self.device_id,
                "measurement_value": self.measurement_value
            }
        )
