//package ablyservice;
//
//import io.ably.lib.auth.TokenDetails;
//import io.ably.lib.rest.AblyRest;
//import io.ably.lib.types.AblyException;
//
//public class TokenGenerator {
//
//    public static void main(String[] args) {
//        String apiKey = "Y5BtgA.rYdcDA:n4g4WjJ841i6_q7lXPraeo-htJ8gxoR0hp7cCr2zMeA";
//        String ablyToken = generateAblyToken(apiKey);
//
//        if (ablyToken != null) {
//            System.out.println("Generated Ably Token: " + ablyToken);
//        }
//    }
//
//    private static String generateAblyToken(String apiKey) {
//        try {
//            AblyRest ably = new AblyRest(apiKey);
//            TokenDetails tokenDetails = ably.auth.requestToken(null, null);
//            return tokenDetails.token;
//        } catch (AblyException e) {
//            System.err.println("An error occurred while generating the Ably token: " + e.getMessage());
//            return null;
//        }
//    }
//}
