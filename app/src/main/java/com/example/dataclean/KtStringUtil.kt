package com.example.dataclean

import java.io.*

class KtStringUtil {
    companion object {

        fun getStrInFile(filePash: String): String {
            val encoding = "UTF-8"
            val file = File(filePash)
            val filelength = file.length()
            val filecontent = ByteArray(filelength!!.toInt())
            try {
                val `in` = FileInputStream(file)
                `in`.read(filecontent)
                `in`.close()
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            return try {
                String(filecontent)
            } catch (e: UnsupportedEncodingException) {
                System.err.println("The OS does not support $encoding")
                e.printStackTrace()
                ""
            }

        }


        fun saveAsFileWriter(fileName: String, contentStr: String) {
            var fwriter: FileWriter? = null
            try {
                // true表示不覆盖原来的内容，而是加到文件的后面。若要覆盖原来的内容，直接省略这个参数就好
                fwriter = FileWriter(fileName)
                fwriter.write(contentStr)
            } catch (ex: IOException) {
                ex.printStackTrace()
            } finally {
                try {
                    fwriter!!.flush()
                    fwriter.close()
                } catch (ex: IOException) {
                    ex.printStackTrace()
                }

            }
        }

        /**
         * 将一个list平均分成num份
         * @param list List<VideoBean>
         * @param num Int
         * @return ArrayList<ArrayList<VideoBean>>
         */
        fun splitList(list: List<VideoBean>, num: Int): ArrayList<ArrayList<VideoBean>> {
            val listSize = list.size
            var stringlist = ArrayList<VideoBean>()
            val stringListHashMap = ArrayList<ArrayList<VideoBean>>()
            for ((aa, av) in list.withIndex()) {
                stringlist.add(av)
                if (((aa + 1) % num == 0) || (aa + 1 == listSize)) {
                    stringListHashMap.add(stringlist)
                    stringlist = ArrayList()
                }
            }
            return stringListHashMap
        }
    }
}