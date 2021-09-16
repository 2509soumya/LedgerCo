package com.ledgerco;

import com.ledgerco.command.Command;
import com.ledgerco.command.CommandHandler;
import com.ledgerco.exceptions.ArgumentMissingException;
import com.ledgerco.exceptions.FileNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Component
@Log4j2
public class CommandProcessor implements CommandLineRunner {
    @Autowired
    CommandHandler commandHandler;

    @Autowired
    ConfigurableApplicationContext ctx;

    @Override
    public void run(String[] args) {
        String filename=null;
        try{
            filename=args[0];
            Path path = Paths.get(filename);
            List<String> filecontent = Files.readAllLines(path);
            runCommandSet(filecontent);
        }catch (IOException ex){
            throw new FileNotFoundException(filename,ex);
        }catch (IndexOutOfBoundsException ex){
            throw new ArgumentMissingException(ex);
        }catch (Exception ex){
            throw ex;
        }finally {
            //execution done
            closeContainer();
        }
    }

    //This function is tested and covered in integration tests
    public void runCommandSet(List<String> filecontent){
        filecontent.stream().map(filestr->{
            String[] str=filestr.split("[\\s]+");
            Command cmd=new Command();
            cmd.setName(str[0]);
            List<String> arglist=new ArrayList<>();
            for(int i=1;i<str.length;i++){
                arglist.add(str[i]);
            }
            cmd.setArguments(arglist);
            return cmd;
        }).forEach(cmd->{
            commandHandler.processCommand(cmd);
        });
    }

    public void closeContainer(){
        ctx.close();
    }
}
