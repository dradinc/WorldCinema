package com.example.worldcinema

data class Movies(
    var movieID : String,
    var name : String,
    var description : String,
    var age : String,
    var images : String,
    var poster : String,
    var tags : Map<String, String>
)

data class Result (val total_count : Int, val incomplete_result : Boolean, val items: List<Movies>)