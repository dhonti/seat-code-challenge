package com.dhontiveros.rover_robot.processor.commons

const val SOME_VALID_JSON =
    """
    {
        "topRightCorner": {
            "x": 5,
            "y": 5
        },
        "roverPosition": {
            "x": 1,
            "y": 2
        },
        "roverDirection": "N",
        "movements": "LMLMLMLMM"
    }
    """

const val SOME_INVALID_JSON =
    """
    { "topRightCorner": "N", "roverDirection": }
    """

const val SOME_VALID_JSON_INVALID_PLATEAU =
    """
    {
        "topRightCorner": {
            "x": -1,
            "y": 5
        },
        "roverPosition": {
            "x": 1,
            "y": 2
        },
        "roverDirection": "N",
        "movements": "LMLMLMLMM"
    }
    """

const val SOME_VALID_JSON_INVALID_START_POSITION =
    """
    {
        "topRightCorner": {
            "x": 5,
            "y": 5
        },
        "roverPosition": {
            "x": -1,
            "y": 2
        },
        "roverDirection": "N",
        "movements": "LMLMLMLMM"
    }
    """

const val VALID_JSON_OUT_PLATEAU =
    """
    {
        "topRightCorner": {
            "x": 2,
            "y": 2
        },
        "roverPosition": {
            "x": 1,
            "y": 1
        },
        "roverDirection": "N",
        "movements": "MMMMM"
    }
    """