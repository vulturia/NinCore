package me.ninjoh.nincore.modules;


import me.ninjoh.nincore.api.Core;
import me.ninjoh.nincore.api.CoreModule;
import me.ninjoh.nincore.api.command.NinCommand;
import me.ninjoh.nincore.api.command.builders.CommandBuilder;
import me.ninjoh.nincore.api.command.builders.SubCommandBuilder;
import me.ninjoh.nincore.subcommands.GetJavaVersion;
import me.ninjoh.nincore.subcommands.IsAnsiConsole;
import me.ninjoh.nincore.subcommands.ListOperators;

public class NinCoreCmdModule extends CoreModule
{

    public NinCoreCmdModule(Core core)
    {
        super(core);
    }


    @Override
    public void onEnable()
    {
        // Register NinCore command it's sub commands.
        this.getLogger().info("Registering NinCore command..");

        CommandBuilder ncB = new CommandBuilder(this.getCore());
        ncB.setName("nincore");
        ncB.setUseStaticDescription(true);
        NinCommand nc = ncB.construct();

        this.getLogger().info("Registering subcommands..");

        nc.addDefaultInfoSubCmd();
        nc.addDefaultHelpSubCmd();

        SubCommandBuilder nc_getjavaversionB = new SubCommandBuilder();
        nc_getjavaversionB.setParentCommand(nc);
        nc_getjavaversionB.setName("getJavaVersion");
        nc_getjavaversionB.setRequiredPermission("nincore.getjavaversion");
        nc_getjavaversionB.addAlias("gjv");
        nc_getjavaversionB.setUseStaticDescription(true);
        nc_getjavaversionB.setStaticDescription("Get the current Java runtime version.");
        nc_getjavaversionB.setExecutor(new GetJavaVersion());
        nc_getjavaversionB.construct();

        SubCommandBuilder nc_isAnsiConsole = new SubCommandBuilder();
        nc_isAnsiConsole.setParentCommand(nc);
        nc_isAnsiConsole.setName("isAnsiConsole");
        nc_isAnsiConsole.setRequiredPermission("nincore.isansiconsole");
        nc_isAnsiConsole.addAlias("iac");
        nc_isAnsiConsole.setUseStaticDescription(true);
        nc_isAnsiConsole.setStaticDescription("Check if the current console is ANSI supported.");
        nc_isAnsiConsole.setExecutor(new IsAnsiConsole());
        nc_isAnsiConsole.construct();

        SubCommandBuilder nc_listOperators = new SubCommandBuilder();
        nc_listOperators.setParentCommand(nc);
        nc_listOperators.setName("listOperators");
        nc_listOperators.setRequiredPermission("nincore.listoperators");
        nc_listOperators.addAlias("lo");
        nc_listOperators.setUseStaticDescription(true);
        nc_listOperators.setStaticDescription("List all server operators.");
        nc_listOperators.setExecutor(new ListOperators());
        nc_listOperators.construct();
    }
}
