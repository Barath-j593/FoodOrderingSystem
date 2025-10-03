package com.foodapp.service;

import com.foodapp.model.Feedback;
import com.foodapp.model.Customer;
import com.foodapp.storage.DataStore;

import java.util.List;

public class FeedbackService {
    public void giveFeedback(Customer c, String message) {
        int id = DataStore.getFeedbacks().size() + 1;
        Feedback f = new Feedback(id, c.getId(), message);
        DataStore.addFeedback(f);
        System.out.println("Thank you for your feedback!");
    }

    public void showAllFeedbacks() {
        List<Feedback> list = DataStore.getFeedbacks();
        if (list.isEmpty()) {
            System.out.println("No feedback available yet.");
        } else {
            for (Feedback f : list) {
                System.out.println(f);
            }
        }
    }
}
