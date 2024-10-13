package com.example.ead_ecommerce_mobile_new.models

class ProductFetch (
    val productId: String,
    val productName: String,
    val productCategory: String,
    val productDescription: String,
    val productQuantity: Int,
    val productVendor: String,
    val productStatus: Boolean,
    val productAvailability: Boolean,
    val productPrice: Double
) {
    override fun toString(): String {
        return "ProductFetch(productId='$productId', productName='$productName', productCategory='$productCategory', " +
                "productDescription='$productDescription', productQuantity=$productQuantity, " +
                "productVendor='$productVendor', productStatus=$productStatus, " +
                "productAvailability=$productAvailability, productPrice=$productPrice)"
    }
}
