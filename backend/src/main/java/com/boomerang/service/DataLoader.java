package com.boomerang.service;

import com.boomerang.model.Opportunity;
import com.boomerang.repository.OpportunityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class DataLoader {

    private OpportunityRepository repository;

    @Autowired
    public DataLoader(OpportunityRepository repository) {
        this.repository = repository;
    }

    @PostConstruct
    private void loadData() {
        String title1 = "Nutrition/Kitchen Program Volunteer";
        String address1 = "Kensington Hospice, 38 Major St, Toronto, ON M5S 2L1";
        String hours1 = "Long-Term (3 months or more)\n" +
                "Minimum 4 hours per week";
        String description1 = " The purpose of the Kitchen Support Volunteer is to create a warm and welcoming, home-like environment in the hub of the house - the kitchen! Kitchen Support Volunteers support the physical comfort and emotional well-being of residents and their families through food. ";
        Double latitude1 = 43.6591399;
        Double longitude1 = -79.4036288;

        String title2 = "CN Tower Call Blitz Volunteer";
        String address2 = "WWF-Canada - Toronto office\n" +
                "410-245 Eglinton Ave East\n" +
                "Toronto, ON\n" +
                "M4P 3J1";
        String hours2 = "Thursday, January 19\n" +
                "\n" +
                "Tuesday, February 7\n" +
                "Thursday, February 9\n" +
                "Thursday, February 16\n" +
                "\n" +
                "Tuesday, March 21\n" +
                "Thursday, March 23\n" +
                "Tuesday, March 28\n" +
                "Thursday, March 30\n" +
                "\n" +
                "All shifts are from 3:00pm - 8:00pm \n";
        String description2 = "Every year WWF-Canada hosts the CN Tower Climb for Nature which is the organization’s most significant volunteer and fundraising event.  The 2-day event sees over 500 volunteers and 6,000 climbers come out in support of WWF-Canada’s conservation work across Canada and raises over $1.2 million!  Every person plays a vital role in raising money to fund the important work we do to protect\n" +
                "\n" +
                "We are running a call outreach project and are looking for friendly, professional, customer service-oriented volunteers to join us in the months leading up the event to reach out to climber and share important event updates. ";
        Double latitude2 = 43.90185051;
        Double longitude2 = -79.45793152;

        String title3 = "PATH Clothing Drive Volunteer";
        String address3 = "First Canadian Place\n" +
                "100 King St W, Toronto, ON M5X 1A9";
        String hours3 = "Monday January 23rd - 7:30am - 9:30am\n" +
                "Monday January 23rd - 9:30am -12:30pm\n" +
                "Monday January 23rd - 12:30pm - 2:30pm\n" +
                "Tuesday January 24th - 7:30am - 9:30am\n" +
                "Tuesday January 24th - 9:30am -12:30pm\n" +
                "Tuesday January 24th - 12:30pm - 2:30pm\n" +
                "Wednesday January 25th -  7:30am - 9:30am\n" +
                "Wednesday January 25th  - 9:30am -12:30pm\n" +
                "Wednesday January 25th - 12:30pm - 2:30pm\n" +
                "Thursday January 26th  - 7:30am - 9:30am\n" +
                "Thursday January 26th 9:30am -12:30pm\n" +
                "Thursday January 26th- 12:30pm - 2:30pm\n" +
                "Friday January 27th- 7:30am - 9:30am\n" +
                "Friday January 27th- 9:30am -12:30pm\n" +
                "Friday January 27th - 12:30pm - 2:30pm";
        String description3 = "Our annual PATH Clothing Drive collects over 6,500 bags of clothing that get directly donated to The Yonge Street Mission to help people who live with low incomes or who are vulnerably housed or those who lack employment.\n" +
                "\n" +
                "We are seeking many reliable and energetic individuals or groups to help out throughout the week.";
        Double latitude3 = 43.6486362;
        Double longitude3 = -79.3817439;

        String title4 = "Volunteer Greeter";
        String address4 = "St. Michael's Hospital, 30 Bond St, Toronto, ON M5B 1W8";
        String hours4 = "Three to four hours, once per week";
        String description4 = "St. Michael’s Hospital is under construction to enhance the spaces where we provide patient care.\n" +
                "In the meantime, construction and the changes it brings are making it more challenging for patients and visitors to find their way around St. Michael’s.\n" +
                "To help them, we are recruiting special volunteers to serve as greeters, guides and general problem solvers.";
        Double latitude4 = 43.6534274;
        Double longitude4 = -79.3777415;

        repository.save(new Opportunity(title1, address1, hours1, description1, latitude1, longitude1));
        repository.save(new Opportunity(title2, address2, hours2, description2, latitude2, longitude2));
        repository.save(new Opportunity(title3, address3, hours3, description3, latitude3, longitude3));
        repository.save(new Opportunity(title4, address4, hours4, description4, latitude4, longitude4));
    }
}
