package com.example.routes


sealed class Exceptions (val message: String)


class UserAlreadyExistsWithEmailException(message: String = "A user already exists with this email"): Exceptions(message)

class InvalidUserIdentifierException(message: String = "Invalid user identifier"): Exceptions(message)

class UserDoesntExistsException(message: String = "This user doesnt exists or may have been deleted"): Exceptions(message)

class InvalidUpdateParamException(message: String = "Invaild update user param") : Exceptions(message)

class WorkSpaceDoesntExistException(message: String = "Work Space Doesnt Exists or may have been deleted") : Exceptions(message)

class UserAlreadyAddedToWorkSpaceException(message: String = "User is already added to this work space") : Exceptions(message)