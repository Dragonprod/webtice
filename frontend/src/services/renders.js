import React from "react";
import ListItem from "@mui/material/ListItem";
import ListItemButton from "@mui/material/ListItemButton";
import ListItemText from "@mui/material/ListItemText";

// TODO: Add list items render from backend
export const renderTagsList = (items) => {
  let tags = null;
  if (items !== undefined && items !== null && items.length !== 0) {
    tags = items.map((tag) => (
      <ListItem disablePadding>
        <ListItemButton component="a" href="#align-content">
          <ListItemText primary="align-content" />
        </ListItemButton>
      </ListItem>
    ));
  }
  return tags;
};
