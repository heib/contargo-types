package net.contargo.types;

import org.junit.Assert;
import org.junit.Test;

import java.util.function.BiConsumer;
import java.util.function.Consumer;


/**
 * @author  Aljona Murygina - murygina@synyx.de
 */
public class SupportedLicensePlateCountryTest {

    private BiConsumer<LicensePlateHandler, Class> assertCorrectHandler = (handler, clazz) -> {
        Assert.assertNotNull("Missing handler", handler);
        Assert.assertEquals("Wrong handler implementation", clazz, handler.getClass());
    };

    @Test
    public void ensureCorrectHandlerMatching() {

        // dedicated handler
        assertCorrectHandler.accept(SupportedLicensePlateCountry.GERMANY.getLicensePlateHandler(),
            GermanLicensePlateHandler.class);

        assertCorrectHandler.accept(SupportedLicensePlateCountry.NETHERLANDS.getLicensePlateHandler(),
            DutchLicensePlateHandler.class);

        // default handler
        assertCorrectHandler.accept(SupportedLicensePlateCountry.BELGIUM.getLicensePlateHandler(),
            DefaultLicensePlateHandler.class);
        assertCorrectHandler.accept(SupportedLicensePlateCountry.SWITZERLAND.getLicensePlateHandler(),
            DefaultLicensePlateHandler.class);
        assertCorrectHandler.accept(SupportedLicensePlateCountry.FRANCE.getLicensePlateHandler(),
            DefaultLicensePlateHandler.class);
        assertCorrectHandler.accept(SupportedLicensePlateCountry.POLAND.getLicensePlateHandler(),
            DefaultLicensePlateHandler.class);
        assertCorrectHandler.accept(SupportedLicensePlateCountry.CZECH_REPUBLIC.getLicensePlateHandler(),
            DefaultLicensePlateHandler.class);
    }


    @Test
    public void ensureCanHazzCountryCode() {

        Assert.assertEquals("Wrong country code", "D", SupportedLicensePlateCountry.GERMANY.getCountryCode());
    }


    @Test
    public void ensureCanGetCountryByCountryCode() {

        LicensePlateCountry country = SupportedLicensePlateCountry.forCountryCode("D");

        Assert.assertEquals("Wrong country", SupportedLicensePlateCountry.GERMANY, country);
    }


    @Test
    public void ensureThrowsIfTryingToGetCountryByEmptyCountryCode() {

        Consumer<String> assertNotEmpty = countryCode -> {
            try {
                SupportedLicensePlateCountry.forCountryCode(countryCode);
                Assert.fail(String.format("Should throw if trying to get country by country code: '%s'", countryCode));
            } catch (IllegalArgumentException ex) {
                // Expected
            }
        };

        assertNotEmpty.accept(null);
        assertNotEmpty.accept("");
        assertNotEmpty.accept(" ");
    }


    @Test(expected = IllegalArgumentException.class)
    public void ensureThrowsIfTryingToGetCountryByUnknownCountryCode() {

        SupportedLicensePlateCountry.forCountryCode("XY");
    }
}
