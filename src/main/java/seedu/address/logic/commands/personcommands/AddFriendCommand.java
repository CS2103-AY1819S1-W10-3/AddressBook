package seedu.address.logic.commands.personcommands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Given two persons, add friends for each other using two displayed indexes from the address book.
 * Friendships must be bilateral, for example person A and B must be friends with each other.
 *
 * @author agendazhang
 */
public class AddFriendCommand extends Command {

    public static final String COMMAND_WORD = "addFriend";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": For two persons identified by the index number used in the displayed person list, "
            + "add friend for each other.\n"
            + "Parameters: INDEX,INDEX (both must be a positive integer and different from each other)\n"
            + "Example: " + COMMAND_WORD + " 1,2";

    public static final String MESSAGE_ADD_FRIEND_SUCCESS = "Friends added: %1$s, %2$s";

    private final Index indexes;

    public AddFriendCommand(Index indexes) {
        this.indexes = indexes;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (indexes.getZeroBased() >= lastShownList.size()
                || indexes.getZeroBased2() >= lastShownList.size()
                || indexes.getZeroBased() == indexes.getZeroBased2()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person person1 = lastShownList.get(indexes.getZeroBased());
        Person person2 = lastShownList.get(indexes.getZeroBased2());
        Person newPerson1 = new Person(person1);
        Person newPerson2 = new Person(person2);
        ArrayList<Person> friendList1 = newPerson1.getFriends();
        ArrayList<Person> friendList2 = newPerson2.getFriends();
        friendList1.add(person2);
        friendList2.add(person1);
        model.updatePerson(person1, newPerson1, person2, newPerson2);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_ADD_FRIEND_SUCCESS, person1.getName(),
                person2.getName()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddFriendCommand // instanceof handles nulls
                && indexes.equals(((AddFriendCommand) other).indexes)); // state check;
    }
}
