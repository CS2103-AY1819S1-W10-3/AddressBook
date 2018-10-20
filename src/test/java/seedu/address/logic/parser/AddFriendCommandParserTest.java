package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.personcommands.AddFriendCommand;

/**
 * Parses input arguments and creates a new AddFriendCommand object
 */
public class AddFriendCommandParserTest {

    private AddFriendCommandParser parser = new AddFriendCommandParser();

    @Test
    public void parseValidArgsReturnsAddFriendCommand() {
        assertParseSuccess(parser, "1,2", new AddFriendCommand(Index.fromZeroBased(INDEX_FIRST.getZeroBased(),
                INDEX_SECOND.getZeroBased())));
    }

    @Test
    public void parseInvalidArgsThrowsParseException() {
        assertParseFailure(parser, "a,b", String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddFriendCommand.MESSAGE_USAGE));
    }
}
