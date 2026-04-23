package com.objects.organization.education;

import com.Global.*;
import com.objects.culture.tenet.group.groups.EducationGroups;

import java.util.HashMap;
import java.util.Map;

public class EduManager {
    private static final Map<String,Education> eduInstances = new HashMap<>();

    public static Education get(String s){
        return eduInstances.get(s);
    }
    public static void register(Education edu){
        eduInstances.put(edu.getID(), edu);
    }


    public static final Education PRIMARY_SCHOOL_EDUCATION = new Education(
            EducationGroups.PRIMARY_SCHOOL_EDUCATION, "primary", "Primary School Education",
            "Foundational schooling covering literacy, numeracy, and core subjects for young children."
    );
    public static final Education SECONDARY_SCHOOL_EDUCATION = new Education(
            EducationGroups.SECONDARY_SCHOOL_EDUCATION, "secondary", "Secondary School Education",
            "Intermediate schooling building on primary education, preparing students for higher study or work."
    );
    public static final Education APPRENTICE_EDUCATION = new Education(
            EducationGroups.APPRENTICE_EDUCATION, "apprenticeship", "Apprenticeship Education",
            "Hands-on vocational training under a skilled master, blending practical work with foundational trade knowledge."
    );
    public static final Education TECHNICAL_SCHOOL_EDUCATION = new Education(
            EducationGroups.TECHNICAL_SCHOOL_EDUCATION, "technical", "Technical School Education",
            "Structured instruction in specialized trades and technical disciplines for skilled labor and industry."
    );
    public static final Education UNDERGRADUATE_EDUCATION = new Education(
            EducationGroups.UNDERGRADUATE_EDUCATION, "undergraduate", "Undergraduate Education",
            "University-level study leading to a bachelor's degree across academic and professional disciplines."
    );
    public static final Education GRADUATE_EDUCATION = new Education(
            EducationGroups.GRADUATE_EDUCATION, "graduate", "Graduate Education",
            "Advanced academic study beyond a bachelor's degree, emphasizing research and specialized expertise."
    );
    public static final Education DOCTORATE_EDUCATION  = new Education(
            EducationGroups.POST_GRADUATE_EDUCATION, "doctor", "Doctorate",
            "Advanced post-graduate study culminating in a doctoral degree, representing the highest level of academic achievement in a given field."
    );
    public static final Education LAWYER_EDUCATION = new Education(
            EducationGroups. POST_GRADUATE_EDUCATION, "lawyer", "Legal Education",
            "Post-graduate study of law and jurisprudence, training students in legal reasoning, procedure, and the application of justice."
    );
    public static final Education MEDICAL_DOCTOR_EDUCATION = new Education(
            EducationGroups.POST_GRADUATE_EDUCATION, "medical_doctor", "Medical Education",
            "Rigorous post-graduate clinical and academic training preparing students to diagnose, treat, and care for patients."
    );
    public static final Education PRIVATE_SCHOOL_PRIMARY_EDUCATION = new Education(
            EducationGroups.PRIVATE_SCHOOL_PRIMARY_EDUCATION, "private_primary", "Private School Primary Education",
            "Fee-based primary schooling offering smaller class sizes, enriched curricula, and greater family oversight."
    );
    public static final Education PRIVATE_SCHOOL_SECONDARY_EDUCATION = new Education(
            EducationGroups.PRIVATE_SCHOOL_SECONDARY_EDUCATION, "private_secondary", "Private School Secondary Education",
            "Privately funded secondary schooling emphasizing academic rigor, discipline, and preparation for higher study."
    );
    public static final Education RELIGIOUS_SCHOOL_PRIMARY_EDUCATION = new Education(
            EducationGroups.RELIGIOUS_SCHOOL_PRIMARY_EDUCATION, "religious_primary", "Religious School Primary Education",
            "Primary schooling rooted in religious doctrine, interweaving faith instruction with standard academic subjects."
    );
    public static final Education RELIGIOUS_SCHOOL_SECONDARY_EDUCATION = new Education(
            EducationGroups.RELIGIOUS_SCHOOL_SECONDARY_EDUCATION, "religious_secondary", "Religious School Secondary Education",
            "Secondary schooling integrating religious teaching with academic study, shaping both faith and intellect."
    );
    public static final Education OFFICER_EDUCATION = new Education(
            EducationGroups.OFFICER_EDUCATION, "officer", "Officer Training",
            "Rigorous academic and military instruction preparing candidates for commissioned leadership roles."
    );
    public static final Education SOLDIER_BASIC_EDUCATION = new Education(
            EducationGroups.SOLDIER_BASIC_EDUCATION, "basic", "Basic Training",
            "Introductory military conditioning instilling discipline, fitness, and fundamental combat skills in new recruits."
    );
    public static final Education SOLDIER_TECHNICAL_EDUCATION = new Education(
            EducationGroups.SOLDIER_TECHNICAL_EDUCATION, "soldier_technical", "Technical Training",
            "Specialized military instruction in technical fields such as engineering, logistics, or communications."
    );
    public static final Education HOMESCHOOL_SCHOOL_PRIMARY_EDUCATION = new Education(
            EducationGroups.HOMESCHOOL_SCHOOL_PRIMARY_EDUCATION, "homeschool_primary", "Homeschooled Primary Education",
            "Family-directed primary instruction tailored to the child's pace, values, and household environment."
    );
    public static final Education HOMESCHOOL_SCHOOL_SECONDARY_EDUCATION = new Education(
            EducationGroups.HOMESCHOOL_SCHOOL_SECONDARY_EDUCATION, "homeschool_secondary", "Homeschooled Secondary Education",
            "Home-based secondary instruction allowing flexible, parent-guided study across core academic subjects."
    );
    public static final Education HOMESCHOOL_DOMESTIC_EDUCATION = new Education(
            EducationGroups.HOMESCHOOL_DOMESTIC_EDUCATION, "domestic", "Domestic Education",
            "Informal household education focused on practical life skills, domestic crafts, and apprentice-adjacent trades.");
}
