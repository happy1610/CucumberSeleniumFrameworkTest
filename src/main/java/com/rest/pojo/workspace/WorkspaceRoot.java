package com.rest.pojo.workspace;

import com.rest.pojo.workspace.Workspace;

public class WorkspaceRoot {

    public Workspace workspace;

    public WorkspaceRoot() {}

    public WorkspaceRoot(Workspace workspace){
        this.workspace = workspace;
    }

    public Workspace getWorkspace() {
        return workspace;
    }

    public void setWorkspace(Workspace workspace) {
        this.workspace = workspace;
    }
}
