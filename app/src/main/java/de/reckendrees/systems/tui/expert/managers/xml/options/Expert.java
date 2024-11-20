package de.reckendrees.systems.tui.expert.managers.xml.options;

import de.reckendrees.systems.tui.expert.managers.xml.XMLPrefsManager;
import de.reckendrees.systems.tui.expert.managers.xml.classes.XMLPrefsElement;
import de.reckendrees.systems.tui.expert.managers.xml.classes.XMLPrefsSave;

public enum Expert implements XMLPrefsSave {

    show_help{
        @Override
        public String defaultValue() {
            return "true";
        }

        @Override
        public String info() {
            return "Show help button";
        }
    },
    show_call{
        @Override
        public String defaultValue() {
            return "true";
        }

        @Override
        public String info() {
            return "Show call button";
        }
    },
    show_exit{
        @Override
        public String defaultValue() {
            return "true";
        }

        @Override
        public String info() {
            return "Show exit button";
        }
    },
    exit_password{
        @Override
        public String defaultValue() {
            return "";
        }

        @Override
        public String info() {
            return "Password for exit command";
        }
    },
    use_root{
        @Override
        public String defaultValue() {
            return "false";
        }

        @Override
        public String info() {
            return "Use root commands";
        }
    },
    numbering_notes{
        @Override
        public String defaultValue() {
            return "true";
        }

        @Override
        public String info() {
            return "Automatically list notes with numbers.";
        }
    },
    custom_command{
        @Override
        public String defaultValue() {
            return " echo use config -set custom_command [command]    for example: curl -s http://wttr.in/dortmund\\?0T\\&lang=de";
        }

        @Override
        public String type() {
            return XMLPrefsSave.TEXT;
        }

        @Override
        public String info() {
            return "Set custom command, which output is shown on the screen";
        }
    },
    custom_command_timeout{
        @Override
        public String defaultValue() {
            return "60";
        }

        @Override
        public String type() {
            return XMLPrefsSave.INTEGER;
        }

        @Override
        public String info() {
            return "Define how often(in seconds) the custom command is executed";
        }
    },
    custom_command_size{
        @Override
        public String defaultValue() {
            return "13";
        }

        @Override
        public String type() {
            return XMLPrefsSave.INTEGER;
        }

        @Override
        public String info() {
            return "Custom command output size size";
        }
    },
    show_custom_command {
        @Override
        public String defaultValue() {
            return "true";
        }

        @Override
        public String info() {
            return "If false, custom command output output will be hidden";
        }
    },
    custom_command_index {
        @Override
        public String defaultValue() {
            return "7";
        }

        @Override
        public String type() {
            return XMLPrefsSave.INTEGER;
        }

        @Override
        public String info() {
            return "This is used to order the labels on top of the screen";
        }
    },
    termux_timeout{
        @Override
        public String defaultValue() {
            return "300";
        }

        @Override
        public String type() {
            return XMLPrefsSave.INTEGER;
        }

        @Override
        public String info() {
            return "How much seconds to wait for a termux command output";
        }
    };


    @Override
    public XMLPrefsElement parent() {
        return XMLPrefsManager.XMLPrefsRoot.EXPERT;
    }

    @Override
    public String label() {
        return name();
    }

    @Override
    public String[] invalidValues() {
        return null;
    }

    @Override
    public String getLowercaseString() {
        return label();
    }

    @Override
    public String getString() {
        return label();
    }
    @Override
    public String type() {
        return XMLPrefsSave.BOOLEAN;
    }
}
