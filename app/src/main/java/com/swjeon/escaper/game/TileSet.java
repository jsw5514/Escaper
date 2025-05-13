package com.swjeon.escaper.game;

import com.fasterxml.jackson.annotation.*;

public class TileSet {
    private long columns;
    private String image;
    private long imageheight;
    private long imagewidth;
    private long margin;
    private String name;
    private long spacing;
    private long tilecount;
    private String tiledversion;
    private long tileheight;
    private long tilewidth;
    private String type;
    private String version;

    @JsonProperty("columns")
    public long getColumns() { return columns; }
    @JsonProperty("columns")
    public void setColumns(long value) { this.columns = value; }

    @JsonProperty("image")
    public String getImage() { return image; }
    @JsonProperty("image")
    public void setImage(String value) { this.image = value; }

    @JsonProperty("imageheight")
    public long getImageheight() { return imageheight; }
    @JsonProperty("imageheight")
    public void setImageheight(long value) { this.imageheight = value; }

    @JsonProperty("imagewidth")
    public long getImagewidth() { return imagewidth; }
    @JsonProperty("imagewidth")
    public void setImagewidth(long value) { this.imagewidth = value; }

    @JsonProperty("margin")
    public long getMargin() { return margin; }
    @JsonProperty("margin")
    public void setMargin(long value) { this.margin = value; }

    @JsonProperty("name")
    public String getName() { return name; }
    @JsonProperty("name")
    public void setName(String value) { this.name = value; }

    @JsonProperty("spacing")
    public long getSpacing() { return spacing; }
    @JsonProperty("spacing")
    public void setSpacing(long value) { this.spacing = value; }

    @JsonProperty("tilecount")
    public long getTilecount() { return tilecount; }
    @JsonProperty("tilecount")
    public void setTilecount(long value) { this.tilecount = value; }

    @JsonProperty("tiledversion")
    public String getTiledversion() { return tiledversion; }
    @JsonProperty("tiledversion")
    public void setTiledversion(String value) { this.tiledversion = value; }

    @JsonProperty("tileheight")
    public long getTileheight() { return tileheight; }
    @JsonProperty("tileheight")
    public void setTileheight(long value) { this.tileheight = value; }

    @JsonProperty("tilewidth")
    public long getTilewidth() { return tilewidth; }
    @JsonProperty("tilewidth")
    public void setTilewidth(long value) { this.tilewidth = value; }

    @JsonProperty("type")
    public String getType() { return type; }
    @JsonProperty("type")
    public void setType(String value) { this.type = value; }

    @JsonProperty("version")
    public String getVersion() { return version; }
    @JsonProperty("version")
    public void setVersion(String value) { this.version = value; }
}
