package mycompany.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TNonblockingServerTransport;
import org.apache.thrift.transport.layered.TFramedTransport;

import thrift.service.ComputeServer;

public class ServerMain {
    private static final Logger logger = LogManager.getLogger(ServerMain.class);

    public static void main(String[] args) {
        try {
            ThriftTestImpl workImpl = new ThriftTestImpl();
            TProcessor processor = new ComputeServer.Processor<ComputeServer.Iface>(workImpl);
      
            TNonblockingServerTransport transport = new TNonblockingServerSocket(9999);
      
            TThreadedSelectorServer.Args servArgs = new TThreadedSelectorServer.Args(transport);
            servArgs.processor(processor);
            servArgs.transportFactory(new TFramedTransport.Factory());
            servArgs.protocolFactory(new TBinaryProtocol.Factory());
      
            TServer server = new TThreadedSelectorServer(servArgs);
            System.out.println("Running TThreadedSelectorServer Server");
      
            server.serve();

        } catch (Exception e) {
            logger.error(e);
        }
    }
}