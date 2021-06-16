package y.w.concurrency.gpar
// Doesn't work...
import groovyx.gpars.GParsExecutorsPool
import groovyx.gpars.dataflow.stream.DataflowStream

def logReader = new BufferedReader(
                new FileReader("C:/awy.local/workdir/Tools/lab/e2etools/e2e/src/test/resources/logfilter/core-log1.log"))
def dataFlowStream = new DataflowStream()
def stringBuilder  = new StringBuilder()

def doReadFile(logReader, dataFlowStream) {
    def line
    while (true) {
        line = logReader.readLine()
        if (!line) { dataFlowStream << "__DONE__"; return }
        dataFlowStream << line
        if (line.endsWith("#~")) { dataFlowStream << "__DONE__"; return }
    }
}

GParsExecutorsPool.withPool {
    doReadFile(logReader, dataFlowStream)
}

while (true) {
    def str = dataFlowStream.val
    if (str == '__DONE__') break
    stringBuilder.append(str)
}

println stringBuilder.toString()
