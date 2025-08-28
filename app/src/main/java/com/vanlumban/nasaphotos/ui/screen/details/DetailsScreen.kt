import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavBackStackEntry
import coil.compose.AsyncImage
import com.vanlumban.nasaphotos.data.models.NasaPhoto
import kotlinx.serialization.json.Json
import java.net.URLDecoder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    backStackEntry: NavBackStackEntry
) {
    val encodedJson = backStackEntry.arguments?.getString("photoJson")
    val jsonString = encodedJson?.let { URLDecoder.decode(it, "UTF-8") }
    val photo = jsonString?.let { Json.decodeFromString<NasaPhoto>(it) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // Image
        AsyncImage(
            model = photo?.url ?: "",
            contentDescription = photo?.title ?: "",
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Title
        Text(
            text = photo?.title ?: "-",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Author & Date
        Text(
            text = "By ${photo?.copyright ?: "-"} on ${photo?.date ?: "-"}",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Description
        Text(
            text = photo?.explanation ?: "-",
            style = MaterialTheme.typography.bodyMedium,
            lineHeight = 20.sp
        )
    }
}