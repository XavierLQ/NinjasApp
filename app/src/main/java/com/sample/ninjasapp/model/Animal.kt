package com.sample.ninjasapp.model

data class Animal(
    val name: String,
    val taxonomy: Taxonomy,
    val characteristics: Characteristics
)

data class Taxonomy(
    val kingdom: String,
    val phylum: String,
    val clazz: String,
    val order: String,
    val family: String,
    val genus: String,
    val scientific_name: String
)

data class Characteristics(
    val common_name: String? = null,
    val slogan: String? = null,
    val lifespan: String? = null,
    val wingspan: String? = null,
    val habitat: String? = null,
    val prey: String? = null,
    val predators: String? = null
)