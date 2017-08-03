package org.kaipan.www.socket.http;

import java.util.Properties;

import org.kaipan.www.socket.controller.ToutiaoController;
import org.kaipan.www.socket.core.Log;
import org.kaipan.www.socket.core.MessageBuffer;
import org.kaipan.www.socket.core.IServer;
import org.kaipan.www.socket.core.SocketProcessor;
import org.kaipan.www.socket.util.Utils;

public class HttpServer extends IServer
{
    public HttpServer(HttpConfig config)
    {
        super(config);

        createSocketProcessor();
    }
    
    public HttpConfig getConfig() 
    {
        return (HttpConfig)iconfig;
    }

    @Override
    protected void createSocketProcessor()
    {
        this.processor = new SocketProcessor(getConfig());
        this.processor.init(new HttpMessageReaderFactory(), new MessageBuffer(), new MessageBuffer(), new HttpMessageProcessor(getConfig()));
        this.processor.addControllerMap("/toutiao", new ToutiaoController());
    }
    
    public static void main(String[] args) 
    {
        HttpConfig config = new HttpConfig();
        
        String path = null;
        if ( args.length > 0 ) path = args[0];
        
        Properties property = null;
        if ( path == null ) {
            String jarHome = Utils.getJarHome(config);
            
            property = Utils.loadConfigFile(jarHome + "/http-server.properties");
        }
        else {
            property = Utils.loadConfigFile(path);
            if ( property == null ) {
                Log.write("Usage: java -jar http-server-{version}.jar "
                        + "\"path to file http-server.properties\"");
                return;
            }
        }
        config.load(property);
        
        HttpServer server = new HttpServer(config);
        server.start();
    }
}
