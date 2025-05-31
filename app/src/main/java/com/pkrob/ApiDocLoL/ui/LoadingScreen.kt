import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.pkrob.ApiDocLoL.R
import com.pkrob.ApiDocLoL.ui.theme.*
import kotlinx.coroutines.delay

@Composable
fun LoadingScreen() {
    // Animation pour le texte
    val infiniteTransition = rememberInfiniteTransition(label = "loading")
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "alpha"
    )
    
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        GradientStart,
                        GradientMiddle,
                        GradientEnd
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Try to use Lottie animation, fallback to CircularProgressIndicator
            val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading_animation))
            
            if (composition != null) {
                LottieAnimation(
                    composition = composition,
                    iterations = LottieConstants.IterateForever,
                    modifier = Modifier.size(120.dp)
                )
            } else {
                // Fallback: Custom loading indicator
                Box(
                    modifier = Modifier.size(120.dp),
                    contentAlignment = Alignment.Center
                ) {
                    // Outer ring
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(100.dp)
                            .graphicsLayer { rotationZ = rotation },
                        strokeWidth = 4.dp,
                        color = GoldPrimary.copy(alpha = 0.3f),
                        trackColor = Color.Transparent
                    )
                    
                    // Inner ring
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(60.dp)
                            .graphicsLayer { rotationZ = -rotation },
                        strokeWidth = 3.dp,
                        color = BlueAccent,
                        trackColor = Color.Transparent
                    )
                    
                    // Center ring
                    CircularProgressIndicator(
                        modifier = Modifier.size(30.dp),
                        strokeWidth = 2.dp,
                        color = GoldAccent,
                        trackColor = Color.Transparent
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            Text(
                text = "League of Legends",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = GoldPrimary.copy(alpha = alpha),
                    fontSize = 24.sp
                )
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Chargement en cours...",
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = TextSecondaryDark.copy(alpha = alpha),
                    fontSize = 16.sp
                )
            )
        }
    }
}
