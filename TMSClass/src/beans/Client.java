/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 
package beans;

/**
 *
 * @author Abree
 */
public class Client {

    private String ClientId = " ";
    private String Name = " ";
    private String Address = " ";
    private String Contact = " ";
    private String Fax = " ";
    private String ContactPerson = " ";
    private String Comment = " ";
    
    public Client(){
    
    }
    
    public void clear(){
        ClientId = " ";
        Name = " ";
        Address = " ";
        Contact = " ";
        Fax = " ";
        ContactPerson = " ";
        Comment = " ";
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public String getClientId() {
        return ClientId;
    }

    public void setClientId(String ClientId) {
        this.ClientId = ClientId;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String Comment) {
        this.Comment = Comment;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String Contact) {
        this.Contact = Contact;
    }

    public String getContactPerson() {
        return ContactPerson;
    }

    public void setContactPerson(String ContactPerson) {
        this.ContactPerson = ContactPerson;
    }

    public String getFax() {
        return Fax;
    }

    public void setFax(String Fax) {
        this.Fax = Fax;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }
    
    
    
}
