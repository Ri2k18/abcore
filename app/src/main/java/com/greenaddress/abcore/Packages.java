package com.greenaddress.abcore;

import android.content.Context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Packages {

    public final static String GLIBC_MAJOR = "2.24";
    public final static String CORE_V = "0.13.0";
    private final static String GLIBC_MINOR = "2";
    private final static String CORE_URL = "https://bitcoin.org/bin/%s";
    private final static String KNOTS_CORE_URL = "https://bitcoinknots.org/files/0.13.x/%s";

    public final static List<PkgH> ARCH_PACKAGES = new ArrayList<>(
            Arrays.asList(
                    new PkgH(String.format("glibc/glibc-%s-%s", GLIBC_MAJOR, GLIBC_MINOR),
                            Arrays.asList(
                                    "armhf7e162f4ac00caa4944c5b264a67864dea29c52f22995863641d0c69e83bed042",
                                    "arm64db24c58d9f2501ee622f631d081333fda50c020eaf2bcb043c9473198fa34d44",
                                    "amd6487721ded792e35b8fd2110d4ac91dc229a9cbbd2fd612d21991f7d68a34e195b",
                                    "i38657a56380c7b57f0882b659d7f44d52082c6d4428dbc51392a3fe707b31ebb82a"
                            )),
                    new PkgH("gcc-libs/gcc-libs-6.1.1-5",
                            Arrays.asList(
                                    "armhf4f2a79d1dbc9d2c09a79b90299610ae09c9f373fcf61251228bf109ad2cf3e90",
                                    "arm64f888d2c6b4155796aa5b89c4ba65e88fdb02e85de65d0d5702d3deef9f738e60",
                                    "amd6480c6e151f9993bad7159f7cb758f2424b2219a0f5cf9e4856a635077898cefd6",
                                    "i3866f7db497d6af8e2e5490f59153c3e368c38da3ee813121ed31d5d9d595486904"
                            ))
            ));

    public final static PkgH CORE_PACKAGE = new PkgH(String.format("bitcoin-core-%s/bitcoin-%s", CORE_V, CORE_V),
            Arrays.asList(
                    "armhf7c657ec6f6a5dbb93b9394da510d5dff8dd461df8b80a9410f994bc53c876303",
                    "arm64f94123e37530f9de25988ff93e5568a93aa5146f689e63fb0ec1f962cf0bbfcd",
                    "amd64bcc1e42d61f88621301bbb00512376287f9df4568255f8b98bc10547dced96c8",
                    "i386d6da2801dd9d92183beea16d0f57edcea85fc749cdc2abec543096c8635ad244"
            ));

    public final static PkgH KNOTS_CORE_PACKAGE = new PkgH(String.format("%s.knots20160814/bitcoin-%s.knots20160814", CORE_V, CORE_V),
            Arrays.asList(
                    "armhf6642058817c3e5762c8654986f0fde518e67be8aaec2eeab8534daf88d92f0ec",
                    "arm646b6d5d717d7e9597708b84c9dd097a69c6b940cdfaf4dfa74e330fbb58b795fe",
                    "amd64cbacc956f7e9987f3ed5a60b30d2afcb6dbcde0811d0007a80a1cebf36caa599",
                    "i3866805d773fb64034bf6717eb732a5ff56a4fd71322e92280496f444c011081fc8"
            ));

    private static String getRepo(final Context c, final String arch) {
        if (arch.equals("amd64") || arch.equals("i386"))
            return "archive.archlinux.org/packages";
        else
            return "tardis.tiny-vps.com/aarm/packages";
    }

    static String getPackageUrl(final Packages.PkgH pkg, final Context c, final String arch) {
        final boolean isArmArchitecture = !arch.equals("amd64") && !arch.equals("i386");
        final String osArch = Utils.getArchLinuxArchitecture(arch);
        final String fileArch = arch.equals("armhf") ? "armv7h" : osArch;
        final String template = "http://%s/%s/%s-" + (isArmArchitecture ? fileArch : osArch) + ".pkg.tar.xz";
        final String repo = getRepo(c, arch);
        return String.format(template, repo, pkg.pkg.charAt(0), pkg.pkg);
    }

    static String getCorePackageUrl(final Packages.PkgH pkg, final String arch) {
        final String packageName = arch == null ? Utils.getCorePkgsName(): Utils.getCorePkgsArch(arch);
        final String path = String.format("%s-%s.tar.gz", pkg.pkg, packageName);
        if (pkg.pkg.contains("knots"))
            return String.format(Packages.KNOTS_CORE_URL, path);
        else
            return String.format(Packages.CORE_URL, path);
    }

    public static class PkgH {
        final String pkg;
        final List<String> archHash;

        PkgH(final String pkg, final List<String> archHash) {
            this.pkg = pkg;
            this.archHash = archHash;
        }
    }
}