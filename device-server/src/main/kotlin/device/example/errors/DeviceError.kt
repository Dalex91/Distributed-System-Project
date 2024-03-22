package device.example.errors

sealed class DeviceError(val message: String) {
    class NotFound(message: String) : DeviceError(message)
    class BadRequest(message: String) : DeviceError(message)
    class BadCredentials(message: String) : DeviceError(message)
    class BadRole(message: String) : DeviceError(message)
    class Unauthorized(message: String) : DeviceError(message)
    class Forbidden(message: String) : DeviceError(message)
}