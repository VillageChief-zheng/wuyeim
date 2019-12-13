package com.wuye.piaoliuim.bean;

import com.chuange.basemodule.BaseData;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @ClassName UserData
 * @Description
 * @Author VillageChief
 * @Date 2019/12/6 17:39
 */
public class UserData extends BaseData {
    @SerializedName("data")
    public Res res;

    public class Res implements Serializable {
        @SerializedName("user")
        public User user;

        public class User implements Serializable {

            private String userId;
            private String userName;
            private String sex;
            private String headPortrait;
            private int age;
            private String duty;
            private String dutyId;
            private String partyStanding;
            private String education;
            private String educationId;
            private String nation;
            private String telephone;
            private String branchName;
            private int individualPoints;
            private String organizationRanking;
            private String guardState;
            private String userState;
            private String groupId;
            private String orgId;
            private String branchId;
            private String password;
            private String idcard;
            private String judge;
            public void setUserId(String userId) {
                this.userId = userId;
            }
            public String getUserId() {
                return userId;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }
            public String getUserName() {
                return userName;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }
            public String getSex() {
                return sex;
            }

            public void setHeadPortrait(String headPortrait) {
                this.headPortrait = headPortrait;
            }
            public String getHeadPortrait() {
                return headPortrait;
            }

            public void setAge(int age) {
                this.age = age;
            }
            public int getAge() {
                return age;
            }

            public void setDuty(String duty) {
                this.duty = duty;
            }
            public String getDuty() {
                return duty;
            }

            public void setDutyId(String dutyId) {
                this.dutyId = dutyId;
            }
            public String getDutyId() {
                return dutyId;
            }

            public void setPartyStanding(String partyStanding) {
                this.partyStanding = partyStanding;
            }
            public String getPartyStanding() {
                return partyStanding;
            }

            public void setEducation(String education) {
                this.education = education;
            }
            public String getEducation() {
                return education;
            }

            public void setEducationId(String educationId) {
                this.educationId = educationId;
            }
            public String getEducationId() {
                return educationId;
            }

            public void setNation(String nation) {
                this.nation = nation;
            }
            public String getNation() {
                return nation;
            }

            public void setTelephone(String telephone) {
                this.telephone = telephone;
            }
            public String getTelephone() {
                return telephone;
            }

            public void setBranchName(String branchName) {
                this.branchName = branchName;
            }
            public String getBranchName() {
                return branchName;
            }



            public void setIndividualPoints(int individualPoints) {
                this.individualPoints = individualPoints;
            }
            public int getIndividualPoints() {
                return individualPoints;
            }

            public void setOrganizationRanking(String organizationRanking) {
                this.organizationRanking = organizationRanking;
            }
            public String getOrganizationRanking() {
                return organizationRanking;
            }

            public void setGuardState(String guardState) {
                this.guardState = guardState;
            }
            public String getGuardState() {
                return guardState;
            }

            public void setUserState(String userState) {
                this.userState = userState;
            }
            public String getUserState() {
                return userState;
            }

            public void setGroupId(String groupId) {
                this.groupId = groupId;
            }
            public String getGroupId() {
                return groupId;
            }

            public void setOrgId(String orgId) {
                this.orgId = orgId;
            }
            public String getOrgId() {
                return orgId;
            }

            public void setBranchId(String branchId) {
                this.branchId = branchId;
            }
            public String getBranchId() {
                return branchId;
            }

            public void setPassword(String password) {
                this.password = password;
            }
            public String getPassword() {
                return password;
            }

            public void setIdcard(String idcard) {
                this.idcard = idcard;
            }
            public String getIdcard() {
                return idcard;
            }

            public String getJudge() {
                return judge;
            }

            public void setJudge(String judge) {
                this.judge = judge;
            }
        }

    }
}
