package y.w.concurrency.gpar

import groovyx.gpars.GParsExecutorsPool
import groovyx.gpars.dataflow.DataflowQueue
class LogReaderStr {
    boolean EOF = false
    def logReader = new BufferedReader(
                    new FileReader("C:/awy.local/workdir/Tools/lab/e2etools/e2e/src/test/resources/logfilter/core-log1.log"))
    DataflowQueue dataQueue = new DataflowQueue()

    boolean isEOF() { return EOF }
    
    void doReadFile(logReader, DataflowQueue queue) {
        String line
        while (true) {
            line = logReader.readLine()
            if (!line) { queue << "___EoF___"; return }
            queue << line
            if (line.endsWith("#~")) { queue << "___DonE___"; return }
        }
    }
    
    String readLine() {
        if (EOF) return null
        String str
        StringBuilder stringBuilder  = new StringBuilder()
        GParsExecutorsPool.withPool {
            doReadFile(logReader, dataQueue)
        }
        
        while (true) {
            str = dataQueue.val
            if (str == '___DonE___') break
            else if (str == '___EoF___') { EOF = true; break }
            stringBuilder.append(str + "\n")
        }
        return stringBuilder.toString()
    }
}

def f = new File("./output.txt")
def lr = new LogReaderStr()
while (!lr.isEOF()) f << lr.readLine()