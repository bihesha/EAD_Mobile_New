import com.example.ead_ecommerce_mobile_new.models.ProductFetch

data class Product(
    val id: Id,
    val productName: String,
    val productCategory: String,
    val productDescription: String,
    val productQuantity: Int,
    val productVendor: String,
    val productStatus: Boolean,
    val productAvailability: Boolean,
    val productImage: String,
    val productPrice: Int
)

class Order(
    val id: String,
    val userId: String,
    val email: String,
    val products: List<ProductFetch>,
    val totalPrice: Double,
    val deliveryStatus: String,
    val orderStatus: String,
    val orderNumber: String,
    val isCancel: Boolean,
    val cancellationNote: String?,
    val orderDate: String
)

data class Id(
    val timestamp: String,
    val machine: Int,
    val pid: Int,
    val increment: Int,
    val creationTime: String
)

