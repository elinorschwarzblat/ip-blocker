package utils

import extension.inRange
import extension.to32BinaryString
import java.util.regex.Matcher
import java.util.regex.Pattern

object CidrUtils {

    private val ipPattern: Pattern = Pattern.compile("(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})")
    private val cidrPattern: Pattern = Pattern.compile("(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})/(\\d{1,3})")

    fun rangesListBuilder(cidrIps: List<String>): List<Pair<String, String>> {
        return mutableListOf<Pair<String, String>>().apply {
            cidrIps.forEach { cidrIp ->
                this.add(calculateBinaryRange(cidrIp))
            }
        }.toList()
    }

    fun ipToBinaryString(ip:String): String {
        val matcher: Matcher = ipPattern.matcher(ip)
        if (matcher.matches()) {
            val ipAddress: Int = matchIpAddress(matcher)
            return ipAddress.to32BinaryString()
        } else {
            throw IllegalArgumentException("Invalid IP pattern [$ip]")
        }
    }

    private fun calculateBinaryRange(cidrIp: String) : Pair<String,String>{
        val matcher: Matcher = cidrPattern.matcher(cidrIp)
        if (matcher.matches()) {
            val ip: Int = matchIpAddress(matcher)
            val cidr: Int = matchCidr(matcher)

            // calculate mask
            var mask = 0
            for (j in 0 until cidr) {
                mask = mask or (1 shl 31 - j)
            }

            // calculate network
            val network = ip and mask

            // calculate broadcast
            val broadcast = network or mask.inv()

            return Pair(network.to32BinaryString(), broadcast.to32BinaryString())
        } else {
            throw IllegalArgumentException("Invalid CIDR pattern [$cidrIp]")
        }
    }

    private fun matchIpAddress(matcher: Matcher): Int {
        var addr = 0
        for (i in 1..4) {
            val n: Int = matcher.group(i).toInt().inRange(0, 255)
            addr = addr or (n and 0xff shl 8 * (4 - i))
        }
        return addr
    }

    private fun matchCidr(matcher: Matcher): Int {
        return matcher.group(5).toInt().inRange(1,32)
    }
}