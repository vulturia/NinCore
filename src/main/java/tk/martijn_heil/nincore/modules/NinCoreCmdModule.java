package tk.martijn_heil.nincore.modules;


import tk.martijn_heil.nincore.subcommands.GetJavaVersion;
import tk.martijn_heil.nincore.subcommands.IsAnsiConsole;
import tk.martijn_heil.nincore.subcommands.ListOperators;
import tk.martijn_heil.nincore.api.Core;
import tk.martijn_heil.nincore.api.CoreModule;
import tk.martijn_heil.nincore.api.command.NinCommand;
import tk.martijn_heil.nincore.api.command.builders.CommandBuilder;
import tk.martijn_heil.nincore.api.command.builders.SubCommandBuilder;

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

        new SubCommandBuilder()
                .setParentCommand(nc)
                .setName("getJavaVersion")
                .setRequiredPermission("nincore.getjavaversion")
                .addAlias("gjv")
                .setUseStaticDescription(true)
                .setStaticDescription("Get the current Java runtime version.")
                .setExecutor(new GetJavaVersion())
                .construct();

        new SubCommandBuilder()
                .setParentCommand(nc)
                .setName("isAnsiConsole")
                .setRequiredPermission("nincore.isansiconsole")
                .addAlias("iac")
                .setUseStaticDescription(true)
                .setStaticDescription("Check if the current console is ANSI supported.")
                .setExecutor(new IsAnsiConsole())
                .construct();

        new SubCommandBuilder()
                .setParentCommand(nc)
                .setName("listOperators")
                .setRequiredPermission("nincore.listoperators")
                .addAlias("lo")
                .setUseStaticDescription(true)
                .setStaticDescription("List all server operators.")
                .setExecutor(new ListOperators())
                .construct();
    }
}
