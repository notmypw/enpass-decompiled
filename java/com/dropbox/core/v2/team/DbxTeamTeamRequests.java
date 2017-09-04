package com.dropbox.core.v2.team;

import com.dropbox.core.DbxApiException;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxWrappedException;
import com.dropbox.core.stone.StoneSerializers;
import com.dropbox.core.v2.DbxRawClientV2;
import com.dropbox.core.v2.async.LaunchEmptyResult;
import com.dropbox.core.v2.async.LaunchEmptyResult.Serializer;
import com.dropbox.core.v2.async.PollArg;
import com.dropbox.core.v2.async.PollEmptyResult;
import com.dropbox.core.v2.async.PollError;
import com.dropbox.core.v2.async.PollErrorException;
import com.dropbox.core.v2.properties.GetPropertyTemplateArg;
import com.dropbox.core.v2.properties.GetPropertyTemplateResult;
import com.dropbox.core.v2.properties.ListPropertyTemplateIds;
import com.dropbox.core.v2.properties.ModifyPropertyTemplateError;
import com.dropbox.core.v2.properties.ModifyPropertyTemplateErrorException;
import com.dropbox.core.v2.properties.PropertyFieldTemplate;
import com.dropbox.core.v2.properties.PropertyTemplateError;
import com.dropbox.core.v2.properties.PropertyTemplateErrorException;
import java.util.List;

public class DbxTeamTeamRequests {
    private final DbxRawClientV2 client;

    public DbxTeamTeamRequests(DbxRawClientV2 client) {
        this.client = client;
    }

    ListMemberDevicesResult devicesListMemberDevices(ListMemberDevicesArg arg) throws ListMemberDevicesErrorException, DbxException {
        try {
            return (ListMemberDevicesResult) this.client.rpcStyle(this.client.getHost().getApi(), "2/team/devices/list_member_devices", arg, false, Serializer.INSTANCE, Serializer.INSTANCE, Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new ListMemberDevicesErrorException("2/team/devices/list_member_devices", ex.getRequestId(), ex.getUserMessage(), (ListMemberDevicesError) ex.getErrorValue());
        }
    }

    public ListMemberDevicesResult devicesListMemberDevices(String teamMemberId) throws ListMemberDevicesErrorException, DbxException {
        return devicesListMemberDevices(new ListMemberDevicesArg(teamMemberId));
    }

    public DevicesListMemberDevicesBuilder devicesListMemberDevicesBuilder(String teamMemberId) {
        return new DevicesListMemberDevicesBuilder(this, ListMemberDevicesArg.newBuilder(teamMemberId));
    }

    ListMembersDevicesResult devicesListMembersDevices(ListMembersDevicesArg arg) throws ListMembersDevicesErrorException, DbxException {
        try {
            return (ListMembersDevicesResult) this.client.rpcStyle(this.client.getHost().getApi(), "2/team/devices/list_members_devices", arg, false, Serializer.INSTANCE, Serializer.INSTANCE, Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new ListMembersDevicesErrorException("2/team/devices/list_members_devices", ex.getRequestId(), ex.getUserMessage(), (ListMembersDevicesError) ex.getErrorValue());
        }
    }

    public ListMembersDevicesResult devicesListMembersDevices() throws ListMembersDevicesErrorException, DbxException {
        return devicesListMembersDevices(new ListMembersDevicesArg());
    }

    public DevicesListMembersDevicesBuilder devicesListMembersDevicesBuilder() {
        return new DevicesListMembersDevicesBuilder(this, ListMembersDevicesArg.newBuilder());
    }

    ListTeamDevicesResult devicesListTeamDevices(ListTeamDevicesArg arg) throws ListTeamDevicesErrorException, DbxException {
        try {
            return (ListTeamDevicesResult) this.client.rpcStyle(this.client.getHost().getApi(), "2/team/devices/list_team_devices", arg, false, Serializer.INSTANCE, Serializer.INSTANCE, Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new ListTeamDevicesErrorException("2/team/devices/list_team_devices", ex.getRequestId(), ex.getUserMessage(), (ListTeamDevicesError) ex.getErrorValue());
        }
    }

    @Deprecated
    public ListTeamDevicesResult devicesListTeamDevices() throws ListTeamDevicesErrorException, DbxException {
        return devicesListTeamDevices(new ListTeamDevicesArg());
    }

    @Deprecated
    public DevicesListTeamDevicesBuilder devicesListTeamDevicesBuilder() {
        return new DevicesListTeamDevicesBuilder(this, ListTeamDevicesArg.newBuilder());
    }

    public void devicesRevokeDeviceSession(RevokeDeviceSessionArg arg) throws RevokeDeviceSessionErrorException, DbxException {
        try {
            this.client.rpcStyle(this.client.getHost().getApi(), "2/team/devices/revoke_device_session", arg, false, Serializer.INSTANCE, StoneSerializers.void_(), Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new RevokeDeviceSessionErrorException("2/team/devices/revoke_device_session", ex.getRequestId(), ex.getUserMessage(), (RevokeDeviceSessionError) ex.getErrorValue());
        }
    }

    RevokeDeviceSessionBatchResult devicesRevokeDeviceSessionBatch(RevokeDeviceSessionBatchArg arg) throws RevokeDeviceSessionBatchErrorException, DbxException {
        try {
            return (RevokeDeviceSessionBatchResult) this.client.rpcStyle(this.client.getHost().getApi(), "2/team/devices/revoke_device_session_batch", arg, false, Serializer.INSTANCE, Serializer.INSTANCE, Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new RevokeDeviceSessionBatchErrorException("2/team/devices/revoke_device_session_batch", ex.getRequestId(), ex.getUserMessage(), (RevokeDeviceSessionBatchError) ex.getErrorValue());
        }
    }

    public RevokeDeviceSessionBatchResult devicesRevokeDeviceSessionBatch(List<RevokeDeviceSessionArg> revokeDevices) throws RevokeDeviceSessionBatchErrorException, DbxException {
        return devicesRevokeDeviceSessionBatch(new RevokeDeviceSessionBatchArg(revokeDevices));
    }

    public TeamGetInfoResult getInfo() throws DbxApiException, DbxException {
        try {
            return (TeamGetInfoResult) this.client.rpcStyle(this.client.getHost().getApi(), "2/team/get_info", null, false, StoneSerializers.void_(), Serializer.INSTANCE, StoneSerializers.void_());
        } catch (DbxWrappedException ex) {
            throw new DbxApiException(ex.getRequestId(), ex.getUserMessage(), "Unexpected error response for \"get_info\":" + ex.getErrorValue());
        }
    }

    GroupFullInfo groupsCreate(GroupCreateArg arg) throws GroupCreateErrorException, DbxException {
        try {
            return (GroupFullInfo) this.client.rpcStyle(this.client.getHost().getApi(), "2/team/groups/create", arg, false, Serializer.INSTANCE, Serializer.INSTANCE, Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new GroupCreateErrorException("2/team/groups/create", ex.getRequestId(), ex.getUserMessage(), (GroupCreateError) ex.getErrorValue());
        }
    }

    public GroupFullInfo groupsCreate(String groupName) throws GroupCreateErrorException, DbxException {
        return groupsCreate(new GroupCreateArg(groupName));
    }

    public GroupsCreateBuilder groupsCreateBuilder(String groupName) {
        return new GroupsCreateBuilder(this, GroupCreateArg.newBuilder(groupName));
    }

    public LaunchEmptyResult groupsDelete(GroupSelector arg) throws GroupDeleteErrorException, DbxException {
        try {
            return (LaunchEmptyResult) this.client.rpcStyle(this.client.getHost().getApi(), "2/team/groups/delete", arg, false, Serializer.INSTANCE, Serializer.INSTANCE, Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new GroupDeleteErrorException("2/team/groups/delete", ex.getRequestId(), ex.getUserMessage(), (GroupDeleteError) ex.getErrorValue());
        }
    }

    public List<GroupsGetInfoItem> groupsGetInfo(GroupsSelector arg) throws GroupsGetInfoErrorException, DbxException {
        try {
            return (List) this.client.rpcStyle(this.client.getHost().getApi(), "2/team/groups/get_info", arg, false, Serializer.INSTANCE, StoneSerializers.list(Serializer.INSTANCE), Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new GroupsGetInfoErrorException("2/team/groups/get_info", ex.getRequestId(), ex.getUserMessage(), (GroupsGetInfoError) ex.getErrorValue());
        }
    }

    PollEmptyResult groupsJobStatusGet(PollArg arg) throws GroupsPollErrorException, DbxException {
        try {
            return (PollEmptyResult) this.client.rpcStyle(this.client.getHost().getApi(), "2/team/groups/job_status/get", arg, false, PollArg.Serializer.INSTANCE, PollEmptyResult.Serializer.INSTANCE, Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new GroupsPollErrorException("2/team/groups/job_status/get", ex.getRequestId(), ex.getUserMessage(), (GroupsPollError) ex.getErrorValue());
        }
    }

    public PollEmptyResult groupsJobStatusGet(String asyncJobId) throws GroupsPollErrorException, DbxException {
        return groupsJobStatusGet(new PollArg(asyncJobId));
    }

    GroupsListResult groupsList(GroupsListArg arg) throws DbxApiException, DbxException {
        try {
            return (GroupsListResult) this.client.rpcStyle(this.client.getHost().getApi(), "2/team/groups/list", arg, false, Serializer.INSTANCE, Serializer.INSTANCE, StoneSerializers.void_());
        } catch (DbxWrappedException ex) {
            throw new DbxApiException(ex.getRequestId(), ex.getUserMessage(), "Unexpected error response for \"groups/list\":" + ex.getErrorValue());
        }
    }

    public GroupsListResult groupsList() throws DbxApiException, DbxException {
        return groupsList(new GroupsListArg());
    }

    public GroupsListResult groupsList(long limit) throws DbxApiException, DbxException {
        if (limit < 1) {
            throw new IllegalArgumentException("Number 'limit' is smaller than 1L");
        } else if (limit <= 1000) {
            return groupsList(new GroupsListArg(limit));
        } else {
            throw new IllegalArgumentException("Number 'limit' is larger than 1000L");
        }
    }

    GroupsListResult groupsListContinue(GroupsListContinueArg arg) throws GroupsListContinueErrorException, DbxException {
        try {
            return (GroupsListResult) this.client.rpcStyle(this.client.getHost().getApi(), "2/team/groups/list/continue", arg, false, Serializer.INSTANCE, Serializer.INSTANCE, Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new GroupsListContinueErrorException("2/team/groups/list/continue", ex.getRequestId(), ex.getUserMessage(), (GroupsListContinueError) ex.getErrorValue());
        }
    }

    public GroupsListResult groupsListContinue(String cursor) throws GroupsListContinueErrorException, DbxException {
        return groupsListContinue(new GroupsListContinueArg(cursor));
    }

    GroupMembersChangeResult groupsMembersAdd(GroupMembersAddArg arg) throws GroupMembersAddErrorException, DbxException {
        try {
            return (GroupMembersChangeResult) this.client.rpcStyle(this.client.getHost().getApi(), "2/team/groups/members/add", arg, false, Serializer.INSTANCE, Serializer.INSTANCE, Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new GroupMembersAddErrorException("2/team/groups/members/add", ex.getRequestId(), ex.getUserMessage(), (GroupMembersAddError) ex.getErrorValue());
        }
    }

    public GroupMembersChangeResult groupsMembersAdd(GroupSelector group, List<MemberAccess> members) throws GroupMembersAddErrorException, DbxException {
        return groupsMembersAdd(new GroupMembersAddArg(group, members));
    }

    public GroupMembersChangeResult groupsMembersAdd(GroupSelector group, List<MemberAccess> members, boolean returnMembers) throws GroupMembersAddErrorException, DbxException {
        return groupsMembersAdd(new GroupMembersAddArg(group, members, returnMembers));
    }

    GroupsMembersListResult groupsMembersList(GroupsMembersListArg arg) throws GroupSelectorErrorException, DbxException {
        try {
            return (GroupsMembersListResult) this.client.rpcStyle(this.client.getHost().getApi(), "2/team/groups/members/list", arg, false, Serializer.INSTANCE, Serializer.INSTANCE, Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new GroupSelectorErrorException("2/team/groups/members/list", ex.getRequestId(), ex.getUserMessage(), (GroupSelectorError) ex.getErrorValue());
        }
    }

    public GroupsMembersListResult groupsMembersList(GroupSelector group) throws GroupSelectorErrorException, DbxException {
        return groupsMembersList(new GroupsMembersListArg(group));
    }

    public GroupsMembersListResult groupsMembersList(GroupSelector group, long limit) throws GroupSelectorErrorException, DbxException {
        if (limit < 1) {
            throw new IllegalArgumentException("Number 'limit' is smaller than 1L");
        } else if (limit <= 1000) {
            return groupsMembersList(new GroupsMembersListArg(group, limit));
        } else {
            throw new IllegalArgumentException("Number 'limit' is larger than 1000L");
        }
    }

    GroupsMembersListResult groupsMembersListContinue(GroupsMembersListContinueArg arg) throws GroupsMembersListContinueErrorException, DbxException {
        try {
            return (GroupsMembersListResult) this.client.rpcStyle(this.client.getHost().getApi(), "2/team/groups/members/list/continue", arg, false, Serializer.INSTANCE, Serializer.INSTANCE, Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new GroupsMembersListContinueErrorException("2/team/groups/members/list/continue", ex.getRequestId(), ex.getUserMessage(), (GroupsMembersListContinueError) ex.getErrorValue());
        }
    }

    public GroupsMembersListResult groupsMembersListContinue(String cursor) throws GroupsMembersListContinueErrorException, DbxException {
        return groupsMembersListContinue(new GroupsMembersListContinueArg(cursor));
    }

    GroupMembersChangeResult groupsMembersRemove(GroupMembersRemoveArg arg) throws GroupMembersRemoveErrorException, DbxException {
        try {
            return (GroupMembersChangeResult) this.client.rpcStyle(this.client.getHost().getApi(), "2/team/groups/members/remove", arg, false, Serializer.INSTANCE, Serializer.INSTANCE, Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new GroupMembersRemoveErrorException("2/team/groups/members/remove", ex.getRequestId(), ex.getUserMessage(), (GroupMembersRemoveError) ex.getErrorValue());
        }
    }

    public GroupMembersChangeResult groupsMembersRemove(GroupSelector group, List<UserSelectorArg> users) throws GroupMembersRemoveErrorException, DbxException {
        return groupsMembersRemove(new GroupMembersRemoveArg(group, users));
    }

    public GroupMembersChangeResult groupsMembersRemove(GroupSelector group, List<UserSelectorArg> users, boolean returnMembers) throws GroupMembersRemoveErrorException, DbxException {
        return groupsMembersRemove(new GroupMembersRemoveArg(group, users, returnMembers));
    }

    List<GroupsGetInfoItem> groupsMembersSetAccessType(GroupMembersSetAccessTypeArg arg) throws GroupMemberSetAccessTypeErrorException, DbxException {
        try {
            return (List) this.client.rpcStyle(this.client.getHost().getApi(), "2/team/groups/members/set_access_type", arg, false, Serializer.INSTANCE, StoneSerializers.list(Serializer.INSTANCE), Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new GroupMemberSetAccessTypeErrorException("2/team/groups/members/set_access_type", ex.getRequestId(), ex.getUserMessage(), (GroupMemberSetAccessTypeError) ex.getErrorValue());
        }
    }

    public List<GroupsGetInfoItem> groupsMembersSetAccessType(GroupSelector group, UserSelectorArg user, GroupAccessType accessType) throws GroupMemberSetAccessTypeErrorException, DbxException {
        return groupsMembersSetAccessType(new GroupMembersSetAccessTypeArg(group, user, accessType));
    }

    public List<GroupsGetInfoItem> groupsMembersSetAccessType(GroupSelector group, UserSelectorArg user, GroupAccessType accessType, boolean returnMembers) throws GroupMemberSetAccessTypeErrorException, DbxException {
        return groupsMembersSetAccessType(new GroupMembersSetAccessTypeArg(group, user, accessType, returnMembers));
    }

    GroupFullInfo groupsUpdate(GroupUpdateArgs arg) throws GroupUpdateErrorException, DbxException {
        try {
            return (GroupFullInfo) this.client.rpcStyle(this.client.getHost().getApi(), "2/team/groups/update", arg, false, Serializer.INSTANCE, Serializer.INSTANCE, Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new GroupUpdateErrorException("2/team/groups/update", ex.getRequestId(), ex.getUserMessage(), (GroupUpdateError) ex.getErrorValue());
        }
    }

    public GroupFullInfo groupsUpdate(GroupSelector group) throws GroupUpdateErrorException, DbxException {
        return groupsUpdate(new GroupUpdateArgs(group));
    }

    public GroupsUpdateBuilder groupsUpdateBuilder(GroupSelector group) {
        return new GroupsUpdateBuilder(this, GroupUpdateArgs.newBuilder(group));
    }

    ListMemberAppsResult linkedAppsListMemberLinkedApps(ListMemberAppsArg arg) throws ListMemberAppsErrorException, DbxException {
        try {
            return (ListMemberAppsResult) this.client.rpcStyle(this.client.getHost().getApi(), "2/team/linked_apps/list_member_linked_apps", arg, false, Serializer.INSTANCE, Serializer.INSTANCE, Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new ListMemberAppsErrorException("2/team/linked_apps/list_member_linked_apps", ex.getRequestId(), ex.getUserMessage(), (ListMemberAppsError) ex.getErrorValue());
        }
    }

    public ListMemberAppsResult linkedAppsListMemberLinkedApps(String teamMemberId) throws ListMemberAppsErrorException, DbxException {
        return linkedAppsListMemberLinkedApps(new ListMemberAppsArg(teamMemberId));
    }

    ListMembersAppsResult linkedAppsListMembersLinkedApps(ListMembersAppsArg arg) throws ListMembersAppsErrorException, DbxException {
        try {
            return (ListMembersAppsResult) this.client.rpcStyle(this.client.getHost().getApi(), "2/team/linked_apps/list_members_linked_apps", arg, false, Serializer.INSTANCE, Serializer.INSTANCE, Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new ListMembersAppsErrorException("2/team/linked_apps/list_members_linked_apps", ex.getRequestId(), ex.getUserMessage(), (ListMembersAppsError) ex.getErrorValue());
        }
    }

    public ListMembersAppsResult linkedAppsListMembersLinkedApps() throws ListMembersAppsErrorException, DbxException {
        return linkedAppsListMembersLinkedApps(new ListMembersAppsArg());
    }

    public ListMembersAppsResult linkedAppsListMembersLinkedApps(String cursor) throws ListMembersAppsErrorException, DbxException {
        return linkedAppsListMembersLinkedApps(new ListMembersAppsArg(cursor));
    }

    ListTeamAppsResult linkedAppsListTeamLinkedApps(ListTeamAppsArg arg) throws ListTeamAppsErrorException, DbxException {
        try {
            return (ListTeamAppsResult) this.client.rpcStyle(this.client.getHost().getApi(), "2/team/linked_apps/list_team_linked_apps", arg, false, Serializer.INSTANCE, Serializer.INSTANCE, Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new ListTeamAppsErrorException("2/team/linked_apps/list_team_linked_apps", ex.getRequestId(), ex.getUserMessage(), (ListTeamAppsError) ex.getErrorValue());
        }
    }

    @Deprecated
    public ListTeamAppsResult linkedAppsListTeamLinkedApps() throws ListTeamAppsErrorException, DbxException {
        return linkedAppsListTeamLinkedApps(new ListTeamAppsArg());
    }

    @Deprecated
    public ListTeamAppsResult linkedAppsListTeamLinkedApps(String cursor) throws ListTeamAppsErrorException, DbxException {
        return linkedAppsListTeamLinkedApps(new ListTeamAppsArg(cursor));
    }

    void linkedAppsRevokeLinkedApp(RevokeLinkedApiAppArg arg) throws RevokeLinkedAppErrorException, DbxException {
        try {
            this.client.rpcStyle(this.client.getHost().getApi(), "2/team/linked_apps/revoke_linked_app", arg, false, Serializer.INSTANCE, StoneSerializers.void_(), Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new RevokeLinkedAppErrorException("2/team/linked_apps/revoke_linked_app", ex.getRequestId(), ex.getUserMessage(), (RevokeLinkedAppError) ex.getErrorValue());
        }
    }

    public void linkedAppsRevokeLinkedApp(String appId, String teamMemberId) throws RevokeLinkedAppErrorException, DbxException {
        linkedAppsRevokeLinkedApp(new RevokeLinkedApiAppArg(appId, teamMemberId));
    }

    public void linkedAppsRevokeLinkedApp(String appId, String teamMemberId, boolean keepAppFolder) throws RevokeLinkedAppErrorException, DbxException {
        linkedAppsRevokeLinkedApp(new RevokeLinkedApiAppArg(appId, teamMemberId, keepAppFolder));
    }

    RevokeLinkedAppBatchResult linkedAppsRevokeLinkedAppBatch(RevokeLinkedApiAppBatchArg arg) throws RevokeLinkedAppBatchErrorException, DbxException {
        try {
            return (RevokeLinkedAppBatchResult) this.client.rpcStyle(this.client.getHost().getApi(), "2/team/linked_apps/revoke_linked_app_batch", arg, false, Serializer.INSTANCE, Serializer.INSTANCE, Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new RevokeLinkedAppBatchErrorException("2/team/linked_apps/revoke_linked_app_batch", ex.getRequestId(), ex.getUserMessage(), (RevokeLinkedAppBatchError) ex.getErrorValue());
        }
    }

    public RevokeLinkedAppBatchResult linkedAppsRevokeLinkedAppBatch(List<RevokeLinkedApiAppArg> revokeLinkedApp) throws RevokeLinkedAppBatchErrorException, DbxException {
        return linkedAppsRevokeLinkedAppBatch(new RevokeLinkedApiAppBatchArg(revokeLinkedApp));
    }

    MembersAddLaunch membersAdd(MembersAddArg arg) throws DbxApiException, DbxException {
        try {
            return (MembersAddLaunch) this.client.rpcStyle(this.client.getHost().getApi(), "2/team/members/add", arg, false, Serializer.INSTANCE, Serializer.INSTANCE, StoneSerializers.void_());
        } catch (DbxWrappedException ex) {
            throw new DbxApiException(ex.getRequestId(), ex.getUserMessage(), "Unexpected error response for \"members/add\":" + ex.getErrorValue());
        }
    }

    public MembersAddLaunch membersAdd(List<MemberAddArg> newMembers) throws DbxApiException, DbxException {
        return membersAdd(new MembersAddArg(newMembers));
    }

    public MembersAddLaunch membersAdd(List<MemberAddArg> newMembers, boolean forceAsync) throws DbxApiException, DbxException {
        return membersAdd(new MembersAddArg(newMembers, forceAsync));
    }

    MembersAddJobStatus membersAddJobStatusGet(PollArg arg) throws PollErrorException, DbxException {
        try {
            return (MembersAddJobStatus) this.client.rpcStyle(this.client.getHost().getApi(), "2/team/members/add/job_status/get", arg, false, PollArg.Serializer.INSTANCE, Serializer.INSTANCE, PollError.Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new PollErrorException("2/team/members/add/job_status/get", ex.getRequestId(), ex.getUserMessage(), (PollError) ex.getErrorValue());
        }
    }

    public MembersAddJobStatus membersAddJobStatusGet(String asyncJobId) throws PollErrorException, DbxException {
        return membersAddJobStatusGet(new PollArg(asyncJobId));
    }

    List<MembersGetInfoItem> membersGetInfo(MembersGetInfoArgs arg) throws MembersGetInfoErrorException, DbxException {
        try {
            return (List) this.client.rpcStyle(this.client.getHost().getApi(), "2/team/members/get_info", arg, false, Serializer.INSTANCE, StoneSerializers.list(Serializer.INSTANCE), Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new MembersGetInfoErrorException("2/team/members/get_info", ex.getRequestId(), ex.getUserMessage(), (MembersGetInfoError) ex.getErrorValue());
        }
    }

    public List<MembersGetInfoItem> membersGetInfo(List<UserSelectorArg> members) throws MembersGetInfoErrorException, DbxException {
        return membersGetInfo(new MembersGetInfoArgs(members));
    }

    MembersListResult membersList(MembersListArg arg) throws MembersListErrorException, DbxException {
        try {
            return (MembersListResult) this.client.rpcStyle(this.client.getHost().getApi(), "2/team/members/list", arg, false, Serializer.INSTANCE, Serializer.INSTANCE, Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new MembersListErrorException("2/team/members/list", ex.getRequestId(), ex.getUserMessage(), (MembersListError) ex.getErrorValue());
        }
    }

    public MembersListResult membersList() throws MembersListErrorException, DbxException {
        return membersList(new MembersListArg());
    }

    public MembersListBuilder membersListBuilder() {
        return new MembersListBuilder(this, MembersListArg.newBuilder());
    }

    MembersListResult membersListContinue(MembersListContinueArg arg) throws MembersListContinueErrorException, DbxException {
        try {
            return (MembersListResult) this.client.rpcStyle(this.client.getHost().getApi(), "2/team/members/list/continue", arg, false, Serializer.INSTANCE, Serializer.INSTANCE, Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new MembersListContinueErrorException("2/team/members/list/continue", ex.getRequestId(), ex.getUserMessage(), (MembersListContinueError) ex.getErrorValue());
        }
    }

    public MembersListResult membersListContinue(String cursor) throws MembersListContinueErrorException, DbxException {
        return membersListContinue(new MembersListContinueArg(cursor));
    }

    void membersRecover(MembersRecoverArg arg) throws MembersRecoverErrorException, DbxException {
        try {
            this.client.rpcStyle(this.client.getHost().getApi(), "2/team/members/recover", arg, false, Serializer.INSTANCE, StoneSerializers.void_(), Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new MembersRecoverErrorException("2/team/members/recover", ex.getRequestId(), ex.getUserMessage(), (MembersRecoverError) ex.getErrorValue());
        }
    }

    public void membersRecover(UserSelectorArg user) throws MembersRecoverErrorException, DbxException {
        membersRecover(new MembersRecoverArg(user));
    }

    LaunchEmptyResult membersRemove(MembersRemoveArg arg) throws MembersRemoveErrorException, DbxException {
        try {
            return (LaunchEmptyResult) this.client.rpcStyle(this.client.getHost().getApi(), "2/team/members/remove", arg, false, Serializer.INSTANCE, Serializer.INSTANCE, Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new MembersRemoveErrorException("2/team/members/remove", ex.getRequestId(), ex.getUserMessage(), (MembersRemoveError) ex.getErrorValue());
        }
    }

    public LaunchEmptyResult membersRemove(UserSelectorArg user) throws MembersRemoveErrorException, DbxException {
        return membersRemove(new MembersRemoveArg(user));
    }

    public MembersRemoveBuilder membersRemoveBuilder(UserSelectorArg user) {
        return new MembersRemoveBuilder(this, MembersRemoveArg.newBuilder(user));
    }

    PollEmptyResult membersRemoveJobStatusGet(PollArg arg) throws PollErrorException, DbxException {
        try {
            return (PollEmptyResult) this.client.rpcStyle(this.client.getHost().getApi(), "2/team/members/remove/job_status/get", arg, false, PollArg.Serializer.INSTANCE, PollEmptyResult.Serializer.INSTANCE, PollError.Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new PollErrorException("2/team/members/remove/job_status/get", ex.getRequestId(), ex.getUserMessage(), (PollError) ex.getErrorValue());
        }
    }

    public PollEmptyResult membersRemoveJobStatusGet(String asyncJobId) throws PollErrorException, DbxException {
        return membersRemoveJobStatusGet(new PollArg(asyncJobId));
    }

    public void membersSendWelcomeEmail(UserSelectorArg arg) throws MembersSendWelcomeErrorException, DbxException {
        try {
            this.client.rpcStyle(this.client.getHost().getApi(), "2/team/members/send_welcome_email", arg, false, Serializer.INSTANCE, StoneSerializers.void_(), Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new MembersSendWelcomeErrorException("2/team/members/send_welcome_email", ex.getRequestId(), ex.getUserMessage(), (MembersSendWelcomeError) ex.getErrorValue());
        }
    }

    MembersSetPermissionsResult membersSetAdminPermissions(MembersSetPermissionsArg arg) throws MembersSetPermissionsErrorException, DbxException {
        try {
            return (MembersSetPermissionsResult) this.client.rpcStyle(this.client.getHost().getApi(), "2/team/members/set_admin_permissions", arg, false, Serializer.INSTANCE, Serializer.INSTANCE, Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new MembersSetPermissionsErrorException("2/team/members/set_admin_permissions", ex.getRequestId(), ex.getUserMessage(), (MembersSetPermissionsError) ex.getErrorValue());
        }
    }

    public MembersSetPermissionsResult membersSetAdminPermissions(UserSelectorArg user, AdminTier newRole) throws MembersSetPermissionsErrorException, DbxException {
        return membersSetAdminPermissions(new MembersSetPermissionsArg(user, newRole));
    }

    TeamMemberInfo membersSetProfile(MembersSetProfileArg arg) throws MembersSetProfileErrorException, DbxException {
        try {
            return (TeamMemberInfo) this.client.rpcStyle(this.client.getHost().getApi(), "2/team/members/set_profile", arg, false, Serializer.INSTANCE, Serializer.INSTANCE, Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new MembersSetProfileErrorException("2/team/members/set_profile", ex.getRequestId(), ex.getUserMessage(), (MembersSetProfileError) ex.getErrorValue());
        }
    }

    public TeamMemberInfo membersSetProfile(UserSelectorArg user) throws MembersSetProfileErrorException, DbxException {
        return membersSetProfile(new MembersSetProfileArg(user));
    }

    public MembersSetProfileBuilder membersSetProfileBuilder(UserSelectorArg user) {
        return new MembersSetProfileBuilder(this, MembersSetProfileArg.newBuilder(user));
    }

    void membersSuspend(MembersDeactivateArg arg) throws MembersSuspendErrorException, DbxException {
        try {
            this.client.rpcStyle(this.client.getHost().getApi(), "2/team/members/suspend", arg, false, Serializer.INSTANCE, StoneSerializers.void_(), Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new MembersSuspendErrorException("2/team/members/suspend", ex.getRequestId(), ex.getUserMessage(), (MembersSuspendError) ex.getErrorValue());
        }
    }

    public void membersSuspend(UserSelectorArg user) throws MembersSuspendErrorException, DbxException {
        membersSuspend(new MembersDeactivateArg(user));
    }

    public void membersSuspend(UserSelectorArg user, boolean wipeData) throws MembersSuspendErrorException, DbxException {
        membersSuspend(new MembersDeactivateArg(user, wipeData));
    }

    void membersUnsuspend(MembersUnsuspendArg arg) throws MembersUnsuspendErrorException, DbxException {
        try {
            this.client.rpcStyle(this.client.getHost().getApi(), "2/team/members/unsuspend", arg, false, Serializer.INSTANCE, StoneSerializers.void_(), Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new MembersUnsuspendErrorException("2/team/members/unsuspend", ex.getRequestId(), ex.getUserMessage(), (MembersUnsuspendError) ex.getErrorValue());
        }
    }

    public void membersUnsuspend(UserSelectorArg user) throws MembersUnsuspendErrorException, DbxException {
        membersUnsuspend(new MembersUnsuspendArg(user));
    }

    AddPropertyTemplateResult propertiesTemplateAdd(AddPropertyTemplateArg arg) throws ModifyPropertyTemplateErrorException, DbxException {
        try {
            return (AddPropertyTemplateResult) this.client.rpcStyle(this.client.getHost().getApi(), "2/team/properties/template/add", arg, false, Serializer.INSTANCE, Serializer.INSTANCE, ModifyPropertyTemplateError.Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new ModifyPropertyTemplateErrorException("2/team/properties/template/add", ex.getRequestId(), ex.getUserMessage(), (ModifyPropertyTemplateError) ex.getErrorValue());
        }
    }

    public AddPropertyTemplateResult propertiesTemplateAdd(String name, String description, List<PropertyFieldTemplate> fields) throws ModifyPropertyTemplateErrorException, DbxException {
        return propertiesTemplateAdd(new AddPropertyTemplateArg(name, description, fields));
    }

    GetPropertyTemplateResult propertiesTemplateGet(GetPropertyTemplateArg arg) throws PropertyTemplateErrorException, DbxException {
        try {
            return (GetPropertyTemplateResult) this.client.rpcStyle(this.client.getHost().getApi(), "2/team/properties/template/get", arg, false, GetPropertyTemplateArg.Serializer.INSTANCE, GetPropertyTemplateResult.Serializer.INSTANCE, PropertyTemplateError.Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new PropertyTemplateErrorException("2/team/properties/template/get", ex.getRequestId(), ex.getUserMessage(), (PropertyTemplateError) ex.getErrorValue());
        }
    }

    public GetPropertyTemplateResult propertiesTemplateGet(String templateId) throws PropertyTemplateErrorException, DbxException {
        return propertiesTemplateGet(new GetPropertyTemplateArg(templateId));
    }

    public ListPropertyTemplateIds propertiesTemplateList() throws PropertyTemplateErrorException, DbxException {
        try {
            return (ListPropertyTemplateIds) this.client.rpcStyle(this.client.getHost().getApi(), "2/team/properties/template/list", null, false, StoneSerializers.void_(), ListPropertyTemplateIds.Serializer.INSTANCE, PropertyTemplateError.Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new PropertyTemplateErrorException("2/team/properties/template/list", ex.getRequestId(), ex.getUserMessage(), (PropertyTemplateError) ex.getErrorValue());
        }
    }

    UpdatePropertyTemplateResult propertiesTemplateUpdate(UpdatePropertyTemplateArg arg) throws ModifyPropertyTemplateErrorException, DbxException {
        try {
            return (UpdatePropertyTemplateResult) this.client.rpcStyle(this.client.getHost().getApi(), "2/team/properties/template/update", arg, false, Serializer.INSTANCE, Serializer.INSTANCE, ModifyPropertyTemplateError.Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new ModifyPropertyTemplateErrorException("2/team/properties/template/update", ex.getRequestId(), ex.getUserMessage(), (ModifyPropertyTemplateError) ex.getErrorValue());
        }
    }

    public UpdatePropertyTemplateResult propertiesTemplateUpdate(String templateId) throws ModifyPropertyTemplateErrorException, DbxException {
        return propertiesTemplateUpdate(new UpdatePropertyTemplateArg(templateId));
    }

    public PropertiesTemplateUpdateBuilder propertiesTemplateUpdateBuilder(String templateId) {
        return new PropertiesTemplateUpdateBuilder(this, UpdatePropertyTemplateArg.newBuilder(templateId));
    }

    GetActivityReport reportsGetActivity(DateRange arg) throws DateRangeErrorException, DbxException {
        try {
            return (GetActivityReport) this.client.rpcStyle(this.client.getHost().getApi(), "2/team/reports/get_activity", arg, false, Serializer.INSTANCE, Serializer.INSTANCE, Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new DateRangeErrorException("2/team/reports/get_activity", ex.getRequestId(), ex.getUserMessage(), (DateRangeError) ex.getErrorValue());
        }
    }

    public GetActivityReport reportsGetActivity() throws DateRangeErrorException, DbxException {
        return reportsGetActivity(new DateRange());
    }

    public ReportsGetActivityBuilder reportsGetActivityBuilder() {
        return new ReportsGetActivityBuilder(this, DateRange.newBuilder());
    }

    GetDevicesReport reportsGetDevices(DateRange arg) throws DateRangeErrorException, DbxException {
        try {
            return (GetDevicesReport) this.client.rpcStyle(this.client.getHost().getApi(), "2/team/reports/get_devices", arg, false, Serializer.INSTANCE, Serializer.INSTANCE, Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new DateRangeErrorException("2/team/reports/get_devices", ex.getRequestId(), ex.getUserMessage(), (DateRangeError) ex.getErrorValue());
        }
    }

    public GetDevicesReport reportsGetDevices() throws DateRangeErrorException, DbxException {
        return reportsGetDevices(new DateRange());
    }

    public ReportsGetDevicesBuilder reportsGetDevicesBuilder() {
        return new ReportsGetDevicesBuilder(this, DateRange.newBuilder());
    }

    GetMembershipReport reportsGetMembership(DateRange arg) throws DateRangeErrorException, DbxException {
        try {
            return (GetMembershipReport) this.client.rpcStyle(this.client.getHost().getApi(), "2/team/reports/get_membership", arg, false, Serializer.INSTANCE, Serializer.INSTANCE, Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new DateRangeErrorException("2/team/reports/get_membership", ex.getRequestId(), ex.getUserMessage(), (DateRangeError) ex.getErrorValue());
        }
    }

    public GetMembershipReport reportsGetMembership() throws DateRangeErrorException, DbxException {
        return reportsGetMembership(new DateRange());
    }

    public ReportsGetMembershipBuilder reportsGetMembershipBuilder() {
        return new ReportsGetMembershipBuilder(this, DateRange.newBuilder());
    }

    GetStorageReport reportsGetStorage(DateRange arg) throws DateRangeErrorException, DbxException {
        try {
            return (GetStorageReport) this.client.rpcStyle(this.client.getHost().getApi(), "2/team/reports/get_storage", arg, false, Serializer.INSTANCE, Serializer.INSTANCE, Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new DateRangeErrorException("2/team/reports/get_storage", ex.getRequestId(), ex.getUserMessage(), (DateRangeError) ex.getErrorValue());
        }
    }

    public GetStorageReport reportsGetStorage() throws DateRangeErrorException, DbxException {
        return reportsGetStorage(new DateRange());
    }

    public ReportsGetStorageBuilder reportsGetStorageBuilder() {
        return new ReportsGetStorageBuilder(this, DateRange.newBuilder());
    }

    TeamFolderMetadata teamFolderActivate(TeamFolderIdArg arg) throws TeamFolderActivateErrorException, DbxException {
        try {
            return (TeamFolderMetadata) this.client.rpcStyle(this.client.getHost().getApi(), "2/team/team_folder/activate", arg, false, Serializer.INSTANCE, Serializer.INSTANCE, Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new TeamFolderActivateErrorException("2/team/team_folder/activate", ex.getRequestId(), ex.getUserMessage(), (TeamFolderActivateError) ex.getErrorValue());
        }
    }

    public TeamFolderMetadata teamFolderActivate(String teamFolderId) throws TeamFolderActivateErrorException, DbxException {
        return teamFolderActivate(new TeamFolderIdArg(teamFolderId));
    }

    TeamFolderArchiveLaunch teamFolderArchive(TeamFolderArchiveArg arg) throws TeamFolderArchiveErrorException, DbxException {
        try {
            return (TeamFolderArchiveLaunch) this.client.rpcStyle(this.client.getHost().getApi(), "2/team/team_folder/archive", arg, false, Serializer.INSTANCE, Serializer.INSTANCE, Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new TeamFolderArchiveErrorException("2/team/team_folder/archive", ex.getRequestId(), ex.getUserMessage(), (TeamFolderArchiveError) ex.getErrorValue());
        }
    }

    public TeamFolderArchiveLaunch teamFolderArchive(String teamFolderId) throws TeamFolderArchiveErrorException, DbxException {
        return teamFolderArchive(new TeamFolderArchiveArg(teamFolderId));
    }

    public TeamFolderArchiveLaunch teamFolderArchive(String teamFolderId, boolean forceAsyncOff) throws TeamFolderArchiveErrorException, DbxException {
        return teamFolderArchive(new TeamFolderArchiveArg(teamFolderId, forceAsyncOff));
    }

    TeamFolderArchiveJobStatus teamFolderArchiveCheck(PollArg arg) throws PollErrorException, DbxException {
        try {
            return (TeamFolderArchiveJobStatus) this.client.rpcStyle(this.client.getHost().getApi(), "2/team/team_folder/archive/check", arg, false, PollArg.Serializer.INSTANCE, Serializer.INSTANCE, PollError.Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new PollErrorException("2/team/team_folder/archive/check", ex.getRequestId(), ex.getUserMessage(), (PollError) ex.getErrorValue());
        }
    }

    public TeamFolderArchiveJobStatus teamFolderArchiveCheck(String asyncJobId) throws PollErrorException, DbxException {
        return teamFolderArchiveCheck(new PollArg(asyncJobId));
    }

    TeamFolderMetadata teamFolderCreate(TeamFolderCreateArg arg) throws TeamFolderCreateErrorException, DbxException {
        try {
            return (TeamFolderMetadata) this.client.rpcStyle(this.client.getHost().getApi(), "2/team/team_folder/create", arg, false, Serializer.INSTANCE, Serializer.INSTANCE, Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new TeamFolderCreateErrorException("2/team/team_folder/create", ex.getRequestId(), ex.getUserMessage(), (TeamFolderCreateError) ex.getErrorValue());
        }
    }

    public TeamFolderMetadata teamFolderCreate(String name) throws TeamFolderCreateErrorException, DbxException {
        return teamFolderCreate(new TeamFolderCreateArg(name));
    }

    List<TeamFolderGetInfoItem> teamFolderGetInfo(TeamFolderIdListArg arg) throws DbxApiException, DbxException {
        try {
            return (List) this.client.rpcStyle(this.client.getHost().getApi(), "2/team/team_folder/get_info", arg, false, Serializer.INSTANCE, StoneSerializers.list(Serializer.INSTANCE), StoneSerializers.void_());
        } catch (DbxWrappedException ex) {
            throw new DbxApiException(ex.getRequestId(), ex.getUserMessage(), "Unexpected error response for \"team_folder/get_info\":" + ex.getErrorValue());
        }
    }

    public List<TeamFolderGetInfoItem> teamFolderGetInfo(List<String> teamFolderIds) throws DbxApiException, DbxException {
        return teamFolderGetInfo(new TeamFolderIdListArg(teamFolderIds));
    }

    TeamFolderListResult teamFolderList(TeamFolderListArg arg) throws TeamFolderListErrorException, DbxException {
        try {
            return (TeamFolderListResult) this.client.rpcStyle(this.client.getHost().getApi(), "2/team/team_folder/list", arg, false, Serializer.INSTANCE, Serializer.INSTANCE, Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new TeamFolderListErrorException("2/team/team_folder/list", ex.getRequestId(), ex.getUserMessage(), (TeamFolderListError) ex.getErrorValue());
        }
    }

    public TeamFolderListResult teamFolderList() throws TeamFolderListErrorException, DbxException {
        return teamFolderList(new TeamFolderListArg());
    }

    public TeamFolderListResult teamFolderList(long limit) throws TeamFolderListErrorException, DbxException {
        if (limit < 1) {
            throw new IllegalArgumentException("Number 'limit' is smaller than 1L");
        } else if (limit <= 1000) {
            return teamFolderList(new TeamFolderListArg(limit));
        } else {
            throw new IllegalArgumentException("Number 'limit' is larger than 1000L");
        }
    }

    void teamFolderPermanentlyDelete(TeamFolderIdArg arg) throws TeamFolderPermanentlyDeleteErrorException, DbxException {
        try {
            this.client.rpcStyle(this.client.getHost().getApi(), "2/team/team_folder/permanently_delete", arg, false, Serializer.INSTANCE, StoneSerializers.void_(), Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new TeamFolderPermanentlyDeleteErrorException("2/team/team_folder/permanently_delete", ex.getRequestId(), ex.getUserMessage(), (TeamFolderPermanentlyDeleteError) ex.getErrorValue());
        }
    }

    public void teamFolderPermanentlyDelete(String teamFolderId) throws TeamFolderPermanentlyDeleteErrorException, DbxException {
        teamFolderPermanentlyDelete(new TeamFolderIdArg(teamFolderId));
    }

    TeamFolderMetadata teamFolderRename(TeamFolderRenameArg arg) throws TeamFolderRenameErrorException, DbxException {
        try {
            return (TeamFolderMetadata) this.client.rpcStyle(this.client.getHost().getApi(), "2/team/team_folder/rename", arg, false, Serializer.INSTANCE, Serializer.INSTANCE, Serializer.INSTANCE);
        } catch (DbxWrappedException ex) {
            throw new TeamFolderRenameErrorException("2/team/team_folder/rename", ex.getRequestId(), ex.getUserMessage(), (TeamFolderRenameError) ex.getErrorValue());
        }
    }

    public TeamFolderMetadata teamFolderRename(String teamFolderId, String name) throws TeamFolderRenameErrorException, DbxException {
        return teamFolderRename(new TeamFolderRenameArg(teamFolderId, name));
    }
}
