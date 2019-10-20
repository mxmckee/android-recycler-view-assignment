package edu.ualr.recyclerviewassignment.model;

public class SectionHeader extends Item{
    private String label;

    public SectionHeader(String label) {
        this.label = label;
        this.section = true;
    }

    public SectionHeader() {
        this.section = true;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
