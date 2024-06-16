package com.crio.jukebox.appConfig;

import com.crio.jukebox.commands.AddSongInPlaylist;
import com.crio.jukebox.commands.CommandInvoker;
import com.crio.jukebox.commands.CreatePlaylistCommand;
import com.crio.jukebox.commands.CreateUserCommand;
import com.crio.jukebox.commands.DeletePlaylistCommand;
import com.crio.jukebox.commands.LoadSongsCommand;
import com.crio.jukebox.commands.PlayPlaylistCommand;
import com.crio.jukebox.commands.PlaySongCommand;
import com.crio.jukebox.repositories.IPlaylistRepository;
import com.crio.jukebox.repositories.ISongRepository;
import com.crio.jukebox.repositories.IUserRepository;
import com.crio.jukebox.repositories.PlaylistRepository;
import com.crio.jukebox.repositories.SongRepository;

import com.crio.jukebox.repositories.UserRepository;
import com.crio.jukebox.services.IPlaylistService;
import com.crio.jukebox.services.ISongService;
import com.crio.jukebox.services.IUserService;
import com.crio.jukebox.services.PlaylistService;
import com.crio.jukebox.services.SongService;
import com.crio.jukebox.services.UserService;

public class ApplicationConfig {
    private final ISongRepository songRepo = new SongRepository();
    private final IUserRepository userRepo = new UserRepository();
    private final IPlaylistRepository playlistRepo = new PlaylistRepository();

    private final ISongService songservice = new SongService(songRepo);
    private final IUserService userservice = new UserService(userRepo);
    private final IPlaylistService playlistservice = new PlaylistService(playlistRepo, songservice, userservice);

    private final LoadSongsCommand loadSongsCommand = new LoadSongsCommand(songservice);
    private final CreateUserCommand createUserCommand = new CreateUserCommand(userservice);
    private final CreatePlaylistCommand createPlaylistCommand = new CreatePlaylistCommand(userservice,playlistservice,songservice);
    private final DeletePlaylistCommand deletePlaylistCommand = new DeletePlaylistCommand(playlistservice, userservice);
    private final AddSongInPlaylist addSongInPlaylist = new AddSongInPlaylist(playlistservice);
    private final PlayPlaylistCommand playPlaylistCommand = new PlayPlaylistCommand(playlistservice);
    private final PlaySongCommand playSongCommand = new PlaySongCommand(playlistservice);
    

    private final CommandInvoker commandInvoker = new CommandInvoker();

    public CommandInvoker getCommandInvoker(){
        commandInvoker.register("LOAD-DATA",loadSongsCommand);
        commandInvoker.register("CREATE-USER",createUserCommand);
        commandInvoker.register("CREATE-PLAYLIST",createPlaylistCommand);
        commandInvoker.register("DELETE-PLAYLIST",deletePlaylistCommand);
        commandInvoker.register("MODIFY-PLAYLIST",addSongInPlaylist);
        commandInvoker.register("PLAY-PLAYLIST",playPlaylistCommand);
        commandInvoker.register("PLAY-SONG",playSongCommand);
        return commandInvoker;
    }
}
