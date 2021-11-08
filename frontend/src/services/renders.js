/* eslint-disable no-unused-vars */
import React from "react";
import ListItem from "@mui/material/ListItem";
import ListItemButton from "@mui/material/ListItemButton";
import ListItemText from "@mui/material/ListItemText";

export const renderTagsList = (items) => {
  let tags = null;
  if (items !== undefined && items !== null && items.length !== 0) {
    tags = items.map((tag) => (
      <ListItem disablePadding>
        <ListItemButton>
          <ListItemText primary={tag.name} />
        </ListItemButton>
      </ListItem>
    ));
  }
  return tags;
};
