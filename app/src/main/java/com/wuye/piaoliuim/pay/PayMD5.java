package com.wuye.piaoliuim.pay;

import java.security.MessageDigest;

/**
 * @ClassName PayMD5
 * @Description
 * @Author VillageChief
 * @Date 2019/12/20 14:36
 */
public class PayMD5 {
    private PayMD5() {}

    public final static String getMessageDigest(byte[] buffer) {
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(buffer);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }


//    RecyclerView.LayoutManager manager = new LinearLayoutManager(
//            getContext(),
//            LinearLayoutManager.HORIZONTAL, false);
//    RecyclerView.LayoutManager manner = new GridLayoutManager(getContext(),
//
//
//
//
//
//
//        secondKillAdapter.setOnItemClickListener(new SecondKillAdapter.OnItemClickListener() {
//        @Override
//        public void onItemClick(View view, int postion) {
//            if (postion==0){
//                startActivity(new Intent(getActivity(), MybranchAct.class));
//            }else if(postion==1){
//
//            }else if(postion==2){
//                Intent intents=new Intent(getActivity(), ParkPartyAct.class);
//                intents.putExtra("parkType","0");
//                startActivity(intents);
//            }else if(postion==3){
//                Intent intent=new Intent(getActivity(),ParkPartyAct.class);
//                intent.putExtra("parkType","1");
//                startActivity(intent);
//
//            }
//
//        }
//    });
}
