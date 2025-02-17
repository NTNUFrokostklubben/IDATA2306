package no.ntnu.learniverseconnect;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class CourseProvider {
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private int providerId;
private String providerName;
}
