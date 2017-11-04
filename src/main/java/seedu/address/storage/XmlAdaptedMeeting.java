package seedu.address.storage;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.person.Name;

//@@author alexanderleegs
/**
 * JAXB-friendly adapted version of the Tag.
 */
public class XmlAdaptedMeeting implements XmlAdaptedClass<Meeting> {

    @XmlElement
    private String meetingName;

    /**
     * Constructs an XmlAdaptedMeeting.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedMeeting() {}

    /**
     * Converts a given Meeting into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedMeeting(Meeting source) {
        meetingName = source.value;
    }

    /**
     * Converts this jaxb-friendly adapted meeting object into the model's Meeting object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Meeting toModelType() throws IllegalValueException {
        return new Meeting(meetingName);
    }

}
