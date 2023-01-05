package studio.attect.demo.vpnservice

import android.content.Context
import android.content.Intent
import android.net.VpnService
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContract
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import studio.attect.demo.vpnservice.ui.theme.AndroidVpnServiceDemoTheme

/**
 * 当前处理的AckId
 */
var currentHandleAckId by mutableStateOf(0L)


class MainActivity : ComponentActivity() {
    private val vpnContent = registerForActivityResult(VpnContent()) {
        if (it) {
            startVpn()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidVpnServiceDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Greeting(name = "Vpn服务")
                        Text(text = "AckId:$currentHandleAckId")
                        Text(text = "设备->网络 字节:${ToNetworkQueueWorker.totalInputCount}")
                        Text(text = "网络->设备 字节:${ToDeviceQueueWorker.totalOutputCount}")
                        Button(onClick = {
                            if (isMyVpnServiceRunning) {
                                stopVpn()
                            } else {
                                prepareVpn()
                            }

                        }) {
                            val text = if (isMyVpnServiceRunning) {
                                "停止VPN"
                            } else {
                                "启动VPN"
                            }
                            Text(text = text)
                        }
                    }
                }
            }
        }

    }

    /**
     * 准备vpn<br>
     * 设备可能弹出连接vpn提示
     */
    private fun prepareVpn() {
        VpnService.prepare(this@MainActivity)?.let {
            vpnContent.launch(it)
        } ?: kotlin.run {
            startVpn()
        }
    }

    /**
     * 启动vpn服务
     */
    private fun startVpn() {
        startService(Intent(this@MainActivity, MyVpnService::class.java))
    }

    /**
     * 停止vpn服务
     */
    private fun stopVpn() {
        startService(Intent(this@MainActivity, MyVpnService::class.java).also { it.action = MyVpnService.ACTION_DISCONNECT })
    }

    class VpnContent : ActivityResultContract<Intent, Boolean>() {
        override fun createIntent(context: Context, input: Intent): Intent {
            return input
        }

        override fun parseResult(resultCode: Int, intent: Intent?): Boolean {
            return resultCode == RESULT_OK
        }
    }

}

@Composable
fun Greeting(name: String) {
    Text(text = name)
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AndroidVpnServiceDemoTheme {
        Greeting("Android")
    }
}

