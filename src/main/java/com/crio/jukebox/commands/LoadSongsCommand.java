package com.crio.jukebox.commands;

import java.io.IOException;
import java.util.List;
import com.crio.jukebox.services.ISongService;

public class LoadSongsCommand implements ICommand{

   
    private final ISongService songservice;

    public LoadSongsCommand(ISongService songService){
        this.songservice = songService;
    }



    @Override
    public void execute(List<String> tokens) {
        try{
            songservice.loadData(tokens.get(1));
            System.out.print("Songs Loaded successfully"+"\n");
        }catch(IOException e){
            e.getMessage();
        }
    }
    
}
