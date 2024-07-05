import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.speedometerpoc.ui.theme.GreenGradient
import com.example.speedometerpoc.ui.theme.SpeedometerPOCTheme
import com.example.speedometerpoc.ui.theme._GREEN
import com.example.speedometerpoc.ui.theme._RED
import com.example.speedometerpoc.ui.theme._YELLOW

const val HALT_SPEED_LIMIT = 0
const val ECONOMY_SPEED_LIMIT = 80
const val OVER_SPEED_LIMIT = 140
const val TOTAL_SPEED_UNITS = 240
const val ARC_ANGLE = 260f
const val ARC_START_ANGLE = 270

@SuppressLint("LongLogTag")
@Composable
fun SpeedTestScreen() {
    var myArcValue by remember { mutableFloatStateOf(0.0f) }
    var mySpeed by remember { mutableFloatStateOf(0.0f) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Header()

        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        ) {
            CircularSpeedIndicator(myArcValue, ARC_ANGLE)
            SpeedValue(mySpeed)
        }

        Row(
            Modifier.fillMaxSize(),
            Arrangement.Center,
        ) {
            IncreaseButton(myArcValue = myArcValue, onArcValueChanged = { newValue ->
                myArcValue = newValue
                mySpeed = newValue * TOTAL_SPEED_UNITS
            })
            StopButton(onStopClicked = {
                myArcValue = 0.0f
                mySpeed = 0.0f
            })
            DecreaseButton(myArcValue = myArcValue, onArcValueChanged = { newValue ->
                myArcValue = newValue
                mySpeed = newValue * TOTAL_SPEED_UNITS
            })
            Log.d("VRK:--SpeedIndicator--", "$myArcValue speed$mySpeed")
        }
    }
}

@Composable
fun SpeedValue(value: Float) {
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "SPEED",
            style = MaterialTheme.typography.headlineMedium,
            fontSize = 20.sp,
            color = Color.Black,
            modifier = Modifier.padding(top = 120.dp, bottom = 5.dp)
        )
        Text(
            text = "%.1f".format(value).toString(),
            fontSize = 35.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )

        var status = "On Halt"
        var statusColor = Color.Black
        if (value > HALT_SPEED_LIMIT && value <= ECONOMY_SPEED_LIMIT) {
            status = "Economy Drive"
            statusColor = Color.Green
        } else if (value > ECONOMY_SPEED_LIMIT && value <= OVER_SPEED_LIMIT) {
            status = "Over Speeding"
            statusColor = Color.Yellow
        } else if (value > OVER_SPEED_LIMIT) {
            status = "Rash Driving"
            statusColor = Color.Red
        }
        Text(
            text = status,
            color = statusColor,
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(top = 80.dp, bottom = 10.dp),
            style = MaterialTheme.typography.headlineMedium
        )
    }
}

@Composable
fun Header() {
    Text(
        text = "Dashboard",
        color = Color.Black,
        modifier = Modifier.padding(top = 80.dp, bottom = 10.dp),
        style = MaterialTheme.typography.headlineMedium,
    )
}

@Composable
fun IncreaseButton(myArcValue: Float, onArcValueChanged: (Float) -> Unit) {
    OutlinedButton(
        onClick = {
            var newValue = myArcValue + 0.0209f // Increment by 5km
            if(newValue >= 1.0f){
                newValue = 1.0f;
            }
            onArcValueChanged(newValue)
        },
        modifier = Modifier.padding(start = 5.dp, top = 60.dp, bottom = 20.dp),
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(width = 2.dp, color = MaterialTheme.colorScheme.onSurface),
    ) {
        Icon(
            Icons.Filled.KeyboardArrowUp, contentDescription = "Localized description",
            modifier = Modifier.size(ButtonDefaults.IconSize)
        )
        Text(
            text = "UP ",
            fontSize = 15.sp,
            color = Color.Black,
            modifier = Modifier.padding(horizontal = 2.dp, vertical = 5.dp)
        )
    }
}

@Composable
fun StopButton(onStopClicked: () -> Unit) {
    OutlinedButton(
        onClick = onStopClicked,
        modifier = Modifier.padding(start = 5.dp, top = 60.dp, bottom = 20.dp),
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(width = 2.dp, color = MaterialTheme.colorScheme.onSurface),

        ) {
        Icon(
            Icons.Filled.Close, contentDescription = "Localized description",
            modifier = Modifier.size(ButtonDefaults.IconSize)
        )
        Text(
            text = "STOP",
            fontSize = 15.sp,
            color = Color.Black,
            modifier = Modifier.padding(horizontal = 2.dp, vertical = 5.dp)
        )
    }
}

@Composable
fun DecreaseButton(myArcValue: Float, onArcValueChanged: (Float) -> Unit) {
    OutlinedButton(
        onClick = {
            var newValue = myArcValue - 0.0209f // Decrement by 5km
            if(newValue <= 0.0f){
                newValue = 0.0f;
            }
            onArcValueChanged(newValue)
        },
        modifier = Modifier.padding(start = 5.dp, top = 60.dp, bottom = 20.dp),
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(width = 2.dp, color = MaterialTheme.colorScheme.onSurface),

        ) {
        Icon(
            Icons.Filled.KeyboardArrowDown, contentDescription = "Localized description",
            modifier = Modifier.size(ButtonDefaults.IconSize)
        )
        Text(
            text = "DOWN",
            fontSize = 15.sp,
            color = Color.Black,
            modifier = Modifier.padding(horizontal = 2.dp, vertical = 5.dp)
        )
    }
}


@Composable
fun CircularSpeedIndicator(value: Float, angle: Float) {
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .padding(50.dp)
    ) {
        drawLines(value, angle)
        drawArcs(value, angle)
    }
}

fun DrawScope.drawArcs(progress: Float, maxValue: Float) {
    //Log.d("VRK::", "drawArcs: progress=$progress  maxValue=$maxValue")
    val startAngle = ARC_START_ANGLE - maxValue / 2
    val sweepAngle = maxValue * progress

    val topLeft = Offset(40f, 40f)
    val size = Size(size.width - 80f, size.height - 80f)

    fun drawGradient() {
        drawArc(
            brush = GreenGradient,
            startAngle = startAngle,
            sweepAngle = sweepAngle,
            useCenter = false,
            topLeft = topLeft,
            size = size,
            style = Stroke(width = 100f, cap = StrokeCap.Round)
        )
    }

    drawGradient()
}

fun DrawScope.drawLines(progress: Float, maxValue: Float) {
    //Log.d("VRK::", "drawLines: progress=$progress  maxValue=$maxValue")
    val oneRotation = maxValue / TOTAL_SPEED_UNITS

    for (i in HALT_SPEED_LIMIT..ECONOMY_SPEED_LIMIT) {
        rotate(i * oneRotation + (180 - maxValue) / 2) {
            drawLine(
                _GREEN,
                Offset(if (i % 10 == 0) 60f else if (i % 5 == 0) 40f else 20f, size.height / 2),
                Offset(0f, size.height / 2),
                6f,
                StrokeCap.Round
            )
        }
    }

    for (i in ECONOMY_SPEED_LIMIT + 1..OVER_SPEED_LIMIT) {
        rotate(i * oneRotation + (180 - maxValue) / 2) {
            drawLine(
                _YELLOW,
                Offset(if (i % 10 == 0) 60f else if (i % 5 == 0) 40f else 20f, size.height / 2),
                Offset(0f, size.height / 2),
                6f,
                StrokeCap.Round
            )
        }
    }

    for (i in OVER_SPEED_LIMIT + 1..TOTAL_SPEED_UNITS) {
        rotate(i * oneRotation + (180 - maxValue) / 2) {
            drawLine(
                _RED,
                Offset(if (i % 10 == 0) 60f else if (i % 5 == 0) 40f else 20f, size.height / 2),
                Offset(0f, size.height / 2),
                6f,
                StrokeCap.Round
            )
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL)
@Composable
fun DefaultPreview() {
    SpeedometerPOCTheme {
        Surface {
            SpeedTestScreen()
        }
    }
}
