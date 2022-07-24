package mycompany.client;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.apache.thrift.transport.layered.TFramedTransport;

import thrift.service.ComputeRequest;
import thrift.service.ComputeResponse;
import thrift.service.ComputeServer;

public class ThriftClient {
    private ComputeServer.Client client = null;
    private TTransport tTransport = null;

    public ThriftClient(String ip,int port){
        try {
            TTransport tTransport = new TFramedTransport(new TSocket(ip,port));
            tTransport.open();
            TProtocol tProtocol = new TBinaryProtocol(tTransport);
            client = new ComputeServer.Client(tProtocol);
        } catch (TTransportException e) {
            e.printStackTrace();
        }
    }

    public ComputeResponse getResponse(ComputeRequest request){
        try {
            ComputeResponse response = client.getComputeResult(request);
            return response;
        } catch (TException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void  close(){
        if (tTransport != null && tTransport.isOpen()){
            tTransport.close();
        }
    }
}