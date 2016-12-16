"""
Finix Python client library.
See ``README.md`` for usage advice.
"""

import os
import re

try:
    import setuptools
except ImportError:
    import distutils.core

    setup = distutils.core.setup
else:
    setup = setuptools.setup

PACKAGE = next((str(s) for s in setuptools.find_packages('.', exclude=("tests", "tests.*"))), None)
PWD = os.path.abspath(os.path.dirname(__file__))
VERSION = (
    re
        .compile(r".*__version__ = '(.*?)'", re.S)
        .match(open(os.path.join(PWD, PACKAGE, "__init__.py")).read())
        .group(1)
)

with open(os.path.join(PWD, "README.md")) as f:
    README = f.read()

requires = [
    "coreapi==1.20.0",
    "finix-wac==0.31"
]

extras_require = {
    "tests": [
    ]
}

scripts = [
    # 'bin/citadel'
]

setup(
    name=PACKAGE,
    version=VERSION,
    url='https://finixpayments.com/',
    license='MIT License',
    author='Finix Payments',
    author_email='dev@finixpayments.com',
    description='Payments API',
    long_description=README,
    packages=[PACKAGE],
    test_suite='nose.collector',
    install_requires=requires,
    tests_require=extras_require['tests'],
    dependency_links=[],
    classifiers=[
        'Intended Audience :: Developers',
        'License :: OSI Approved :: MIT License',
        'Programming Language :: Python',
        'Topic :: Software Development :: Libraries :: Python Modules',
    ],
    include_package_data=True,
    zip_safe=False,
    scripts=scripts,
    extras_require=extras_require,
    setup_requires=['nose>=1.3.7']
)
